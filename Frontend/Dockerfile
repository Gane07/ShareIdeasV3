# Step 1: Use Node.js base image to build the frontend
FROM node:22.11.0-alpine AS build

# Step 2: Set the working directory in the container
WORKDIR /app

# Step 3: Copy package.json and package-lock.json (or yarn.lock) into the container
COPY package*.json ./ 

# Step 4: Install the dependencies for the React app
RUN npm install

# Step 5: Copy the rest of the application code into the container
COPY . . 

# Step 6: Build the React app for production
RUN npm run build

# Step 7: Use Nginx to serve the static files
FROM nginx:alpine

# Step 8: Modify Nginx to expose port 80 (Nginx default)
RUN sed -i 's/listen 80;/listen 80;/' /etc/nginx/conf.d/default.conf

# Step 9: Copy the build files from the first stage to Nginx directory
COPY --from=build /app/build /usr/share/nginx/html

# Step 10: Expose port 80 for the frontend (Nginx default port)
EXPOSE 80

# Step 11: Start Nginx to serve the React app on port 80
CMD ["nginx", "-g", "daemon off;"]
