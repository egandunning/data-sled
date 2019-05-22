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

5. Double click `data-sled.bat`

6. Fill the following fields
	1. Enter desired filename - file extension is automatically set to .psv (if left blank, set to `run_<unix timestamp>`)
	2. Enter directory location to place exported file (if left blank, set to `./data`)
	3. Enter password - required

7. Click `Query & Export` button

8. File path will be displayed in a dialog box upon success

## Command-line Usage (No GUI)

This functionality was introduced in release 0.0.4

1. Put database connection details into `/path/to/connection.txt` (see above for format - step 4 in Usage)

2. Save SQL query in text file `/path/to/query.sql`

3. Copy JDBC drivers into `./jdbc` folder (same as above step 3 in Usage)

4. Run the following: `./data-sled.bat /path/to/connection.txt /path/to/query.sql /output/filename.txt`. Note that `/output/filename.txt` will be overwritten if it already exists.

5. Enter database password when prompted. Upon error, the stack trace will be printed to console. Upon success, the following message will be printed:

```
Query results copied: /output/filename.txt
success
```

