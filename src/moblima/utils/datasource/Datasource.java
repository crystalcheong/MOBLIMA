package moblima.utils.datasource;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import moblima.utils.Catcher;
import moblima.utils.Helper;
import moblima.utils.deserializers.LocalDateDeserializer;
import moblima.utils.deserializers.LocalDateTimeDeserializer;
import org.apache.commons.io.FileUtils;
import org.json.CDL;
import org.json.JSONArray;
import org.json.JSONTokener;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

/**
 * All-purpose datasource parser and serializer
 *
 * @author SC2002 /SS11 Group 1
 * @version 1.0
 */
public class Datasource {
  /**
   * Destination directory
   */
  protected static final String DATA_DIR = "./data/";
  /**
   * Pre-defined API response parser
   */
  protected static final Gson gson = new GsonBuilder().setLenient().setPrettyPrinting()
      .enableComplexMapKeySerialization()
      .registerTypeAdapter(LocalDate.class, new LocalDateDeserializer())
      .registerTypeAdapter(LocalDateTime.class, new LocalDateTimeDeserializer())
      .create();
  /**
   * API endpoint
   */
  protected String ENDPOINT = null;
  /**
   * API key (if required)
   */
  protected String API_KEY;

  /**
   * Retrieves pre-defined API response parser
   *
   * @return gson :Gson
   */
  public static Gson getGson() {
    return gson;
  }

  /// CONVERTERS

  /**
   * Converts List to JsonArray
   *
   * @param list :List
   * @return result :JsonArray
   */
  protected static JsonArray convertToJsonArray(List list) {
    return gson.toJsonTree(list).getAsJsonArray();
  }

  /// REQUESTERS

  /**
   * Save and export list data to CSV
   *
   * @param list           :List
   * @param outputFileName :String
   * @return isSuccess :boolean
   */
  public static boolean serializeData(List list, String outputFileName) {
    Helper.logger("Datasource.serializeData", "Exporting to " + outputFileName + "...");
    return Datasource.serializeDataToCSV(Datasource.convertToJsonArray(list), outputFileName, true);
  }

  /**
   * Serializes JsonArray into CSV file
   *
   * @param responseObj    :JsonArray
   * @param outputFileName :String
   * @param overwrite      :boolean
   * @return isSuccessful :boolean
   */
  public static boolean serializeDataToCSV(JsonArray responseObj, String outputFileName, boolean overwrite) {
    boolean isSuccessful = false;

    if (overwrite && responseObj == null) {
      Helper.logger("ERROR/Datasource.serializeCSV", "Unable to serialize null object");
      return isSuccessful;
    }

    // Configure output path
    String path = DATA_DIR + outputFileName;
    File file = new File(path);

    try {
      // Fetch data from API (only if file doesn't exist)
      if (file.exists() && !overwrite) throw new Catcher("Datasource.serializeCSV", "File already exists at " + path);

      // Save to file (if overwrite)
      String jsonStringified = gson.toJson(responseObj);
      JSONArray jsonArray = new JSONArray(jsonStringified);
      String csvString = CDL.toString(jsonArray);
      if (csvString == null) csvString = CDL.rowToString(jsonArray);

//      Helper.logger("Datasource.serializeDataToCSV", "jsonArray: " + jsonArray);
//      Helper.logger("Datasource.serializeDataToCSV", "csvString: " + csvString);


      isSuccessful = saveCsv(file, csvString, overwrite);
    } catch (Catcher e) {
      isSuccessful = true;
    }

    return isSuccessful;
  }

  /**
   * Delete file boolean.
   *
   * @param fileName the file name
   * @return the boolean
   */
  public static boolean deleteFile(String fileName) {
    // Configure output path
    String path = DATA_DIR + fileName;
    File file = new File(path);

    boolean isDeleted = !file.exists() || file.delete();
    Helper.logger("Datasource.deleteFile", "File " + file.getName() + " deletion " + (isDeleted ? "successful" : "failed"));
    return isDeleted;
  }


  /// SERIALIZERS

  /**
   * Serialize stringified JSON to output file
   *
   * @param outputFile :File
   * @param jsonObject :String
   * @param overwrite  :boolean
   * @return isSaved :boolean
   */
  public static boolean saveJson(File outputFile, String jsonObject, boolean overwrite) {
    boolean isSaved = false;
    FileWriter writer;
    try {
      // Fetch data from API (only if file doesn't exist)
      if (outputFile.exists() && !overwrite)
        throw new Catcher("Datasource.saveJson", "File already exists at " + outputFile.getAbsolutePath());

      // Create parent directories if not exists
      outputFile.getParentFile().mkdirs();
      outputFile.createNewFile();

      // Save to file
      writer = new FileWriter(outputFile.getAbsolutePath());
      writer.write(jsonObject);
      writer.close();

      Helper.logger("Datasource.saveJson", "Successfully fetched from API and output JSON to " + outputFile.getAbsolutePath());
      Helper.logger("Datasource.saveJson", "Output: " + jsonObject);

      isSaved = true;
    } catch (Catcher e) {
      isSaved = true;
    } catch (Exception e) {
      e.getStackTrace();
    }

    return isSaved;
  }

  /**
   * Serialize stringified CSV to output file
   *
   * @param outputFile :File
   * @param csvObject  :String
   * @param overwrite  :boolean
   * @return isSaved :boolean
   */
  public static boolean saveCsv(File outputFile, String csvObject, boolean overwrite) {
    boolean isSaved = true;

    try {
      // Fetch data from API (only if file doesn't exist)
      if (outputFile.exists() && !overwrite)
        throw new Catcher("Datasource.saveCsv", "File already exists at " + outputFile.getAbsolutePath());

      // Create parent directories if not exists
      outputFile.getParentFile().mkdirs();
      outputFile.createNewFile();

      FileUtils.writeStringToFile(outputFile, csvObject, Charset.defaultCharset());

      Helper.logger("Datasource.saveCsv", "Output CSV to " + outputFile.getAbsolutePath());
//      Helper.logger("Datasource.saveCsv", "Output: " + csvObject);

      isSaved = true;
    } catch (Catcher e) {
      isSaved = true;
    } catch (Exception e) {
      e.getStackTrace();
    }

    return isSaved;
  }

  /// SAVERS

  /**
   * Extract JsonArray from CSV file
   *
   * @param fileName :String
   * @return result :JsonArray
   */
  public static JsonArray readArrayFromCsv(String fileName) {
    JsonArray result = null;

    // Configure target path
    String path = DATA_DIR + fileName;
    File file = new File(path);

    try {
      if (!file.exists()) throw new FileNotFoundException("File" + path + " does not exist");
      Helper.logger("Datasource.readArrayFromCsv", "Reading from " + file.getAbsolutePath());

      String content = Files.readString(Paths.get(path));
      JSONArray jsonArray = CDL.toJSONArray(content);
      if (jsonArray == null) jsonArray = CDL.rowToJSONArray(new JSONTokener(content));
      String jsonStringified = jsonArray.toString();
      jsonStringified = jsonStringified.replaceAll("\\[]", "");

      result = gson.fromJson(jsonStringified, JsonArray.class);

      Helper.logger("Datasource.readArrayFromCsv", "Stringified " + jsonStringified);
      Helper.logger("Datasource.readArrayFromCsv", "Result " + result);

    } catch (Exception e) {
      Helper.logger("Datasource.readArrayFromCsv", e.getMessage());
    }

    return result;
  }

  /**
   * Makes paginated requests
   *
   * @param query    :String
   * @param startIdx :int
   * @param endIdx   :int
   * @return result :JsonArray
   */
  public JsonArray requestPagination(String query, int startIdx, int endIdx) {
    JsonArray allResults = new JsonArray();

    for (int i = startIdx; i < endIdx; i++) {
      String pageRequest = query + "&page=" + i;

      JsonElement response = request(pageRequest);
      if (response == null) {
        Helper.logger("Datasource.requestPagination", "Unable to fetch response, possibly the lack of internet connectivity");
        break;
      }
      JsonArray results = (response.getAsJsonObject()).get("results").getAsJsonArray();
      allResults.addAll(results);
    }

    Helper.logger("Datasource.requestPagination", "Output: " + allResults);
    return allResults;
  }

  /// READERS

  /**
   * Makes API request
   *
   * @param query :String
   * @return result :JsonObject
   */
  public JsonElement request(String query) {
    JsonElement responseJson = null;

    if (this.ENDPOINT == null && this.ENDPOINT.isEmpty()) {
      Helper.logger("ERROR/Datasource.request", "ENDPOINT does not exist");
      return responseJson;
    }
    if (this.API_KEY != null) {
      // Prep URI with API Key
      query += "&api_key=" + API_KEY;
    }

    HttpRequest request = HttpRequest.newBuilder().uri(URI.create(ENDPOINT + query)).method("GET", HttpRequest.BodyPublishers.noBody()).build();
    Helper.logger("Datasource.request", "Request URI: " + request.uri().toString());
    HttpResponse<String> response;
    try {
      response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
      if (response.statusCode() != 200)
        throw new Exception("Status " + response.statusCode() + "\nhttps://www.themoviedb.org/documentation/api/status-codes");

      String data = response.body().trim();

      // Convert JSON to JsonElement
      responseJson = gson.fromJson(data, JsonElement.class);

      Helper.logger("Datasource.request", "Endpoint requested: " + request.uri().toString());
      Helper.logger("Datasource.request", "Output: " + data);
    } catch (Exception e) {
      Helper.logger("Datasource.request", e.getMessage());
      return responseJson;
    }

    return responseJson;
  }

}
