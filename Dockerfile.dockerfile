# Use a base image with Tomcat
FROM tomcat:10.1

# Copy the WAR file into the appropriate location in the container
COPY Garage.war /usr/local/tomcat/webapps/

# Set up environment variables or other configurations if needed
ENV JAVA_OPTS="-Xmx256m -Xms256m"

# Expose a port if needed (e.g., 8080 for Tomcat)
EXPOSE 8080

# Command to start Tomcat
CMD ["catalina.sh", "run"]
