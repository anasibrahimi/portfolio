# Portfolio Application

A personal portfolio web application built with Spring Boot.

## Environment Variables for Security

This application uses environment variables for security in production. This approach helps protect sensitive information like database credentials and admin passwords from being exposed in the codebase.

### Setting Up Environment Variables

#### Development Environment

For development, you can use the default values in `application-dev.properties`. No additional setup is required.

#### Production Environment

For production, you need to set the following environment variables:

1. **Set the active profile to production**:
   ```
   SPRING_PROFILES_ACTIVE=prod
   ```

2. **Database Configuration**:
   ```
   DB_URL=jdbc:your_database_url
   DB_DRIVER=your.database.Driver
   DB_USERNAME=your_username
   DB_PASSWORD=your_secure_password
   DB_DIALECT=your.database.Dialect
   ```

3. **Admin Credentials**:
   ```
   ADMIN_USERNAME=your_admin_username
   ADMIN_PASSWORD=your_secure_admin_password
   ```

4. **File Upload Directory** (optional):
   ```
   FILE_UPLOAD_DIR=path/to/uploads
   ```

### Using .env Files

You can create a `.env` file in the root directory of the project with these variables. An example file `.env.example` is provided as a template.

**Important**: Never commit your `.env` file or `application-prod.properties` to version control. They are already added to `.gitignore`.

### Running with Environment Variables

#### Command Line
```bash
export SPRING_PROFILES_ACTIVE=prod
export DB_URL=jdbc:mysql://localhost:3306/portfolio
# Set other variables...
./mvnw spring-boot:run
```

#### In IntelliJ IDEA
1. Edit Run Configuration
2. Add environment variables in the "Environment variables" field
3. Run the application

## Security Best Practices

1. Use strong, unique passwords for database and admin access
2. Regularly rotate credentials
3. Limit database user permissions to only what's necessary
4. In production, consider using a secrets management service instead of environment variables
