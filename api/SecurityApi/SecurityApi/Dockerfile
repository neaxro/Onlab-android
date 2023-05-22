#See https://aka.ms/customizecontainer to learn how to customize your debug container and how Visual Studio uses this Dockerfile to build your images for faster debugging.

# FROM mcr.microsoft.com/dotnet/aspnet:7.0-bullseye-slim AS base
# WORKDIR /app
# EXPOSE 80
# EXPOSE 443

# FROM mcr.microsoft.com/dotnet/sdk:7.0-bullseye-slim AS build
# WORKDIR /src
# COPY ["SecurityApi/SecurityApi.csproj", "SecurityApi/"]
# RUN dotnet restore "SecurityApi/SecurityApi.csproj"
# COPY . .
# WORKDIR "/src/SecurityApi"
# RUN dotnet build "SecurityApi.csproj" -c Release -o /app/build

# FROM build AS publish
# RUN dotnet publish "SecurityApi.csproj" -c Release -o /app/publish /p:UseAppHost=false

# FROM base AS final
# WORKDIR /app
# COPY --from=publish /app/publish .
# ENTRYPOINT ["dotnet", "SecurityApi.dll"]

FROM mcr.microsoft.com/dotnet/aspnet:7.0-bullseye-slim-amd64 AS base
WORKDIR /app

FROM mcr.microsoft.com/dotnet/sdk:7.0 AS build
WORKDIR /src
COPY . .
RUN dotnet restore "SecurityApi.csproj"

FROM build AS publish
RUN dotnet publish "SecurityApi.csproj" -c Release -o /app/publish

FROM base AS final
WORKDIR /app
COPY --from=publish /app/publish .
ENV COMPlus_EnableDiagnostics=0
ENTRYPOINT ["dotnet", "SecurityApi.dll"]

EXPOSE 80
EXPOSE 443