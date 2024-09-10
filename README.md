# Smart Tasks

## Overview

This android application allows any user to see the current available tasks, resolve them, and leave a comment on them if required.

<table>
  <tr>
    <td><img src="https://github.com/user-attachments/assets/8f790e04-0a62-4005-b130-e1e3d7615020" width="500"/></td>
    <td><img src="https://github.com/user-attachments/assets/072057f8-7b57-4dd1-8e28-af002b81fdb5" width="500"/></td>
  </tr>
  <tr>
    <td><img src="https://github.com/user-attachments/assets/88c961f7-0903-4f74-9f38-4ec6ea504643" width="500"/></td>
    <td><img src="https://github.com/user-attachments/assets/e5889587-b9a3-4150-8cc6-9dd9398ecf6c" width="500"/></td>
  </tr>
</table>

## Features

- View tasks for a specific day
- Navigate to the previous or next day, or select a specific date from a calendar.
- View task details, such as the description, title, days left to complete, and target date. 
- Mark tasks as resolved or unresolved, and leave a comment if required.

## Architecture

SmartTasks is built using the Model-View-ViewModel (MVVM) architecture pattern, which helps in separating concerns, improving testability, and making the app more modular. The app uses clean architecture and a multi-module approach to ensure the code remains modularized, readable, and testable.

### Modules

- **Data**: Contains all the data classes required for retrieving data, such as the task list, task details, and the Room database. Also contains a Util package for dates.
- **Domain**: Contains usecases to access the Data layer.
- **Network**: Contains API classes with retrofit integration.
- **UI**: Contains UI components to be utilized by the feature modules, utilizes Compose. 
- **App**: Contains the entry point and setup for the navigation
- **Tasks**: Contains the UI screens and view models that make up the bulk of the app. Utilizes Compose to built the screens and view models to access the domain layer.

## Libraries Used

- **Jetpack Compose**: For building the UI declaratively.
- **Retrofit**: For making network requests to the GitHub API.
- **Kotlin Serialization**: For JSON serialization and deserialization.
- **Kotlin Coroutines**: For managing asynchronous operations.
- **Flow**: For observing data changes in a lifecycle-aware manner.
- **Jetpack Navigation**: For handling navigation between screens.
- **Room**: For local SQL database storage.
- **Hilt**: For dependency management and injection. 

## Installation

### Prerequisites

- Android Studio Koala (2024.1.1) or later 
- Minimum SDK version 26.

### Steps to Run

1. Clone the repository:
   ```bash
   git clone https://github.com/arnoldGoncharenko1/SmartTasks.git
2. **Open the project in Android Studio.**
3. **Sync the project** to download all dependencies.
4. **Run the app** on an emulator or a physical device.

## API Details

The app uses a single GET call

### Get User Information

- **URL**: `http://demo1414406.mockable.io/`
- **Response**: Contains the task list

## Known Issues and Limitations

- The app has no API call to resolve tasks, everything is stored locally. 
- This limitation is minor, but the task list gets replaced by the new tasks that the API picks up each time the app restarts.
- The app currently possesses basic error handling

## Future Enhancements

- **Error Handling**: Add further error handling by showing more user-friendly messages and additional handling for new response types. 
- **Further API integration**: Add API calls for resolving the tasks, properly updating it on the backend so results would be consistent. 
- **Testing**: Add test coverage for every additional module

## Conclusion

SmartTasks demonstrates the use of modern Android development practices, including Jetpack Compose, the MVVM architecture, Hilt, Room, and the multi-module approach to organization.
