<!-- PROJECT LOGO -->
<br />
<div align="center">
  <a href="https://github.com/TasnimAlzahrani/Rihla">
    <img src="src/logo-removebg-preview.png" alt="Logo" width="200" height="200">
  </a>

<h3 align="center">Rihla</h3>

  <p align="center">
    Rihla is a system designed to ease the connection between Umm AL-Quraa University students and bus drivers to help students find a proper bus with a driver they can trust.
  </p>
  <p>This project is still on Evaluation and testing stage :)</p>
</div>



<!-- TABLE OF CONTENTS -->
<details>
  <summary>Table of Contents</summary>
  <ol>
    <li>
      <a href="#about-the-project">About The Project</a>
      <ul>
        <li><a href="#built-with">Built With</a></li>
      </ul>
    </li>
    <li>
      <a href="#getting-started">Getting Started</a>
      <ul>
        <li><a href="#prerequisites">Prerequisites</a></li>
      </ul>
    </li>
    <li><a href="#contact">Contact</a></li>
    <li><a href="#acknowledgments">Acknowledgments</a></li>
  </ol>
</details>


### Built With

* [Java](https://www.oracle.com/java/technologies/java8.html)
* [JavaFX](https://www.oracle.com/java/technologies/javase/javafx-overview.html)
* CSS
* [Hibernate ORM](https://hibernate.org/orm/)
* [MySQL](https://www.mysql.com/)

<p align="right">(<a href="#top">back to top</a>)</p>



<!-- GETTING STARTED -->
## Getting Started

### Prerequisites
#### Notes: 
* Netbeans IDE is used in this tutorial because it supports Hibernate and provides simple configuration wizard while some other IDEs don’t support Hibernate ORM tool in their free versions.
* First: download the MySQL community server + MySQL community workbench from the following link: https://dev.mysql.com/downloads/

First you need to have the database for the application to function, here is the database design: 
<img src= "https://user-images.githubusercontent.com/97178478/156430495-ad5ebad8-26eb-482c-82d0-4b29f0ac8bea.png" hieght = "900" width = "800">

Second, follow these steps:
#### Steps: 
* Right click on the package “rihla”: Create new (Hibernate Configuration Wizard) to create the hibernate.cfg.xml file.
* From the appeared window do the following:
  * Change the folder from “src” to the package folder inside the src --> “src/rihla”
  * Click Next -> then Choose your DB connection (or new DB Connection), If the connection is not specified and you want to create a new Database connection follow the steps bellow: 
    * Choose MySQL(Connector/j driver)
    * Click add and browse to the location of MySQL connector jar file in your computer. I f you could not find the file you can download the version suitable for your MySQL server from here: https://dev.mysql.com/downloads/connector/j/
    * Then click next
    * The following must be considered
      1. Change the database name to be “student”
      2. Enter the password of your MySQL connection
      3. Test the connection before proceeding to the next step, if the connection succeeds, Click Finish.
  * The Hibernate Wizard now will be ready to set the rest of configuration properties as follow:
    1. Choose the JDBC properties from the wizard:
       Add 1-> (if not there)
       Property Name : hibernate.connection.driver_class
       Property Value: com.mysql.jdbc.driver
       Add 2-> (if not there)
       Property Name : hibernate.connection.url
       Property Value: jdbc:mysql://localhost:3306/student
       Add 3-> (if not there)
       Property Name : hibernate.connection.username
       Property Value: Your MySQL username connection
       Add 4->
       Property Name : hibernate.connection.passwords
       Property Value: Your MySQL connection password
    2. Down to Optional property in the same wizard and expand the Configuration
       Property then:
       Add 1->
       Property Name : hibernate.show_sql
       Property Value: true
       Add 2-> (if not there)
       Property Name : hibernate.dialect
       Property Value: org.hibernate.dialect.MySQLDialect
     3. Down and expand the Miscellanies Properties in the same wizard, then:
        Add 1->
        Property Name : hibernate.currenr_session_contect_class
        Property Value: thread
        
*****
<p align="right">(<a href="#top">back to top</a>)</p>




<!-- ROADMAP -->
<!-- ## Roadmap

- [ ] Feature 1
- [ ] Feature 2
- [ ] Feature 3
    - [ ] Nested Feature

See the [open issues](https://github.com/github_username/repo_name/issues) for a full list of proposed features (and known issues).

<p align="right">(<a href="#top">back to top</a>)</p>

 -->

<!-- CONTACT -->
## Contact

Tasneem Alzahrani - [@__Tasn](https://twitter.com/__tasn) - TasneemAliAlzahrani@outlook.com

Project Link: [https://github.com/TasnimAlzahrani/Rihla](https://github.com/TasnimAlzahrani/Rihla)

<p align="right">(<a href="#top">back to top</a>)</p>



<!-- ACKNOWLEDGMENTS -->
## Acknowledgments

* Thanks to Alaa Turkestani for helping me with the buses filters and with the student bus cancelation :).

<p align="right">(<a href="#top">back to top</a>)</p>
