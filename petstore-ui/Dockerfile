FROM node:20 AS builder
WORKDIR /app
COPY package*.json ./
RUN npm install
COPY . .
RUN npm run build

FROM nginx:alpine
WORKDIR /usr/share/nginx/html

# Copy Nginx config and SSL certs
COPY nginx/nginx.conf /etc/nginx/conf.d/default.conf
COPY nginx/cert.pem /etc/nginx/ssl/cert.pem
COPY nginx/key.pem /etc/nginx/ssl/key.pem

COPY --from=builder /app/build .

EXPOSE 443