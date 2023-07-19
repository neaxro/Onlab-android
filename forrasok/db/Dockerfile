FROM mcr.microsoft.com/mssql/server:2017-latest-ubuntu

ENV ACCEPT_EULA=Y

COPY db_letrehozo_script.sql setup.sql
COPY setup_database.sh setup_database.sh
COPY entrypoint.sh entrypoint.sh

#CMD /bin/bash ./entrypoint.sh
#RUN ./opt/mssql/bin/sqlservr & ./setup_database.sh