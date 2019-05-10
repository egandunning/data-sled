## Data Sled

Java GUI application to export database query results to delimited files.

## Usage

1. Download current release [https://github.com/egandunning/data-sled/releases](https://github.com/egandunning/data-sled/releases)

2. Unzip

3. Copy desired JBDC drivers into `jdbc` folder

4. Put default database connection details into `default.txt` - this is a Java properties file.
```
classname=<fully qualified driver class name - required for some but not all JDBC drivers>
username=<required>
connstring=<required - this is a standard JDBC connection string>
```

5. Double click `start.bat`

6. Fill the following fields
	1. Enter desired filename - file extension is automatically set to .psv (if left blank, set to `run_<unix timestamp>`)
	2. Enter directory location to place exported file (if left blank, set to `./data`)
	3. Enter password - required

7. Click `Query & Export` button

8. File path will be displayed in a dialog box upon success

