FROM postgres
ENV POSTGRES_USER rolodex
ENV POSTGRES_PASSWORD D3@dp00!
ADD contacts.sql /docker-entrypoint-initdb.d
EXPOSE 8080