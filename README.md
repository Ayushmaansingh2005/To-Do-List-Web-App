# To-Do List Web Application

## Overview
This project is a Java-based web application for managing a simple To-Do List. Users can add, edit, delete, and view tasks through a web interface. The application uses Java Servlets, JSP, and follows the MVC architecture.

## Features
- Add new tasks
- Edit existing tasks
- Delete tasks
- View all tasks
- Responsive web interface
- Service worker for offline support

## Project Structure
```
pom.xml
src/
  main/
    java/
      Get_Task_Servlet.java
      TaskAddServlet.java
      TaskDeleteServlet.java
      TaskEditServlet.java
    resources/
    webapp/
      index.jsp
      manifest.json
      service-worker.js
      To_Do_Index.html
      WEB-INF/
        web.xml
target/
  classes/
  generated-sources/
    annotations/
  test-classes/
```

### Key Files
- **pom.xml**: Maven configuration file for dependencies and build.
- **Get_Task_Servlet.java**: Handles fetching tasks from the server.
- **TaskAddServlet.java**: Handles adding new tasks.
- **TaskEditServlet.java**: Handles editing tasks.
- **TaskDeleteServlet.java**: Handles deleting tasks.
- **index.jsp**: Main JSP page for displaying the To-Do List.
- **To_Do_Index.html**: HTML page for the To-Do List interface.
- **manifest.json**: Web app manifest for PWA support.
- **service-worker.js**: Service worker for offline capabilities.
- **web.xml**: Servlet configuration and mappings.

## Setup Instructions

### Prerequisites
- Java JDK 8 or higher
- Maven
- Apache Tomcat (or any Java Servlet container)

### Build & Run
1. **Clone the repository**
   ```
   git clone <repo-url>
   ```
2. **Navigate to the project directory**
   ```
   cd To-Do List
   ```
3. **Build the project using Maven**
   ```
   mvn clean install
   ```
4. **Deploy the WAR file**
   - Locate the generated WAR file in `target/`
   - Deploy it to your servlet container (e.g., Tomcat's `webapps` folder)
5. **Start the server**
   - Start Tomcat or your chosen servlet container
6. **Access the application**
   - Open your browser and go to `http://localhost:8080/To-Do List`

## Usage
- Open the web app in your browser.
- Add, edit, or delete tasks using the interface.
- Tasks are managed server-side via Java Servlets.

## Technologies Used
- Java Servlet API
- JSP
- HTML, CSS, JavaScript
- Maven
- Service Worker (for offline support)

## Contributing
1. Fork the repository
2. Create a new branch (`git checkout -b feature/your-feature`)
3. Commit your changes
4. Push to your branch
5. Create a pull request

## License
This project is licensed under the MIT License.

## Author
- Rajput

## Acknowledgements
- Java Servlet documentation
- Maven documentation
- Apache Tomcat
