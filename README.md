### SC2002 Object-Oriented Project — *MOBLIMA*

> A console-based Movie Booking and Listing Management application.<br/>
> - [Demo](https://youtu.be/apbWi2NVMBU)
> - [Documentation](https://ry3ng.github.io/MOBLIMA/)


<br/>

<p align="center">
  <img src="https://user-images.githubusercontent.com/65748007/201518843-d3c4fcfd-d4bd-4eb1-9826-dd8bbdef3c46.png" alt="Project Cover"
    width="960px"
  />
</p>

---

#### 🛠️ Installation and Set Up

  - Clone repository
    ```
    git clone https://github.com/Ry3nG/MOBLIMA.git
    ```

  - Run build executable 
    - Customer mode
      ```
      java -jar build/moblima.jar
      ```
    - Staff mode
      ```
      java -jar build/moblima.jar --staff
      ```
    - Debug mode
      ```
      java -jar build/moblima.jar --debug
      ```

---

<details>
<summary>📂 Project Structure</summary>
<br/>
  
```
📦moblima
 ┣ 📂data
 ┃ ┣ 📜movies.csv
 ┃ ┗ 📜README.md
 ┣ 📂build
 ┃ ┣ 📦moblima.jar
 ┣ 📂diagram
 ┣ 📂docs
 ┣ 📂lib
 ┣ 📂datasource
 ┣ 📂src
 ┣ 📂test
 ┣ 📜README.md
 ```


 [`/build`](./build) - contains the compiled executable of the project<br/> 
 [`/data`](./data) - stores all the serialized data to be utilized <br/>
 [`/diagram`](./diagram) - contains project UML documentation <br/>
 [`/docs`](./docs) - contains the [`Javadoc`](https://ry3ng.github.io/MOBLIMA/) <br/>
 [`/lib`](./lib) - contains the project dependencies<br/> 
 [`/datasource`](./datasource) - internal module to fetch real-world data from various APIs<br/> 
 [`/src`](./src) - contains the project source files<br/> 
 [`/test`](./test) - contains the unit test cases<br/> 


 </details>

---

#### 📑 Data Sources
A working set of serialized data is readily available in the `/data` folder.<br/>

<details>
<summary>🧬 Retrieval and Re-Generation</summary>
<br/>

OPTION `#1`
> To re-generate a clean data set, run the build executable with 
> ```
> java -jar build/moblima.jar --generate --debug
> ```

OPTION `#2`
> ⚠️ This option requires an `TMDB_API_KEY` from <a href="https://developers.themoviedb.org/3" target="_blank">The Movie Database (TMDB)</a><br/>
> 1. Duplicate the `.env.example` and save as `.env`. <br/>
> 2. Store your API KEY as `TMDB_API_KEY=<YOUR API KEY>` <br/>
> 3. Run `App.java` to start the application <br/>

</details>

<br/>


- Movie Data - <a href="https://developers.themoviedb.org/3" target="_blank">The Movie Database (TMDB)</a>
- Public Holiday Data - <a href="https://www.mom.gov.sg/employment-practices/public-holidays" target="_blank">Ministry of Manpower (MOM)</a> / <a href="https://github.com/rjchow/singapore_public_holidays" target="_blank">API</a>


---
####  🧰 Languages & Tools
- Languages <br/>
  <img alt="Java" src="https://img.shields.io/badge/Java-ED8B00?style=for-the-badge&logo=java&logoColor=white" height="25"/>

- Tools, IDE <br/>
  <img alt="Github" src="https://img.shields.io/badge/GitHub-100000?style=for-the-badge&logo=github&logoColor=white" height="25"/>
  <img alt="Github Actions" src="https://img.shields.io/badge/GitHub_Actions-2088FF?style=for-the-badge&logo=github-actions&logoColor=white" height="25"/>
  <img alt="IntelliJ IDEA" src="https://img.shields.io/badge/IntelliJIDEA-000000.svg?style=for-the-badge&logo=intellij-idea&logoColor=white" height="25"/>

---

#### Contributors ✨

<table>
  <tr>
    <td align="center"><a href="https://github.com/crystalcheong"  target="_blank"><img src="https://avatars.githubusercontent.com/u/65748007?v=4?s=100" width="100px;" alt=""/><br /><sub><b>Crystal Cheong</b></sub></a><br /></td>
    <td align="center"><a href="https://github.com/Ry3nG" target="_blank"><img src="https://avatars.githubusercontent.com/u/89843462?v=4?s=100" width="100px;" alt=""/><br /><sub><b>Ryan Gong</b></sub></a><br /></td>
    <td align="center"><a href="https://github.com/min13489" target="_blank"><img src="https://avatars.githubusercontent.com/u/102536776?v=4?s=100" width="100px;" alt=""/><br /><sub><b>Min Min</b></sub></a><br /></td>
    <td align="center"><a href="https://github.com/claraheng" target="_blank"><img src="https://avatars.githubusercontent.com/u/76896985?v=4?s=100" width="100px;" alt=""/><br /><sub><b>Clara Heng</b></sub></a><br /></td>
    <td align="center"><a href="https://github.com/yay1243" target="_blank"><img src="https://avatars.githubusercontent.com/u/103989071?v=4?s=100" width="100px;" alt=""/><br /><sub><b>Han Wen</b></sub></a><br /></td>
  </tr>
</table>

---

*This repository is submitted as a project work for Nanyang Technological University's [SC2002 - Object-Oriented Design & Programming course](https://www.nanyangmods.com/modules/cz2002-object-oriented-design-programming-3-0-au/).*
