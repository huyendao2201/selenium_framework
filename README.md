# Selenium Framework

[![Test Status](https://github.com/<user>/<repo>/actions/workflows/selenium-full.yml/badge.svg)](https://github.com/<user>/<repo>/actions)

[![Allure Report](https://img.shields.io/badge/Allure-Report-orange)](https://<user>.github.io/<repo>/)

## Description
Automation testing framework using Selenium, TestNG, Maven, GitHub Actions, Selenium Grid, and Allure Report.

## Run local
```bash
mvn clean test -Dbrowser=chrome -Dheadless=false -DsuiteXmlFile=src/test/resources/testng-smoke.xml