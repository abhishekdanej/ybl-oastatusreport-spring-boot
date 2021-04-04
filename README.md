# ybl-oastatusreport-spring-boot

This spring boot cli app processes two text files to generate a report. The path to the two files can provided as a command line or in an external properties file. Since i'm using WSL on my Windows PC, I wanted run the via CLI arguments and external config props file on cmd.exe and Linux shell both. That led me to explore the variations in file paths on Windows vs. Linux, and it is interesting to note the differences.

## 1 Dev: Passing arguments to Eclipse during development.

The program requires three arguments. Add them in IDE's Run Configuration > Arguments > Program Arguments.

`--opr_node_file="C:\Users\ADanej\OneDrive - Micro Focus\cases\Yes Bank\obm oa health report\HC_CONFIG&OPR_NODE_OP_YBL\OPR_NODE_OP.csv" --opr_hc_file="C:\Users\ADanej\OneDrive - Micro Focus\cases\Yes Bank\obm oa health report\HC_CONFIG&OPR_NODE_OP_YBL\HC_CONFIG_OP.csv" --output_dir="C:\Users\ADanej\OneDrive - Micro Focus\cases\Yes Bank\obm oa health report\HC_CONFIG&OPR_NODE_OP_YBL"`

## 2 Run: Passing arguments in CLI or via external properties file in WSL2

### 2a WSL with CLI Arguments

```
java -jar target/ybl-oastatusreport-spring-boot-0.0.1-SNAPSHOT.jar \
--opr_node_file="/mnt/c/Users/ADanej/OneDrive - Micro Focus/cases/Yes Bank/obm oa health report/HC_CONFIG&OPR_NODE_OP_YBL/OPR_NODE_OP.csv" \
--opr_hc_file="/mnt/c/Users/ADanej/OneDrive - Micro Focus/cases/Yes Bank/obm oa health report/HC_CONFIG&OPR_NODE_OP_YBL/HC_CONFIG_OP.csv" \
--output_dir="/mnt/c/Users/ADanej/OneDrive - Micro Focus/cases/Yes Bank/obm oa health report/HC_CONFIG&OPR_NODE_OP_YBL"
```

### 2b WSL with external props file

`java -jar target/ybl-oastatusreport-spring-boot-0.0.1-SNAPSHOT.jar  --spring.config.location=file:///mnt/c/Users/ADanej/Documents/My\ Stuff/workspace_sts4/ybl-oastatusreport-spring-boot/config.properties`

or without using 'file' word

`java -jar target/ybl-oastatusreport-spring-boot-0.0.1-SNAPSHOT.jar  --spring.config.location=/mnt/c/Users/ADanej/Documents/My\ Stuff/workspace_sts4/ybl-oastatusreport-spring-boot/config.properties`

Properties file entry samples, with and without spaces in file path.

```
	#1 WORKS
	opr_node_file=/mnt/c/Users/ADanej/OneDrive\ -\ Micro\ Focus/cases/Yes\ Bank/obm\ oa\ health\ report/HC_CONFIG&OPR_NODE_OP_YBL/OPR_NODE_OP.csv
	opr_hc_file=/mnt/c/Users/ADanej/OneDrive\ -\ Micro\ Focus/cases/Yes\ Bank/obm\ oa\ health\ report/HC_CONFIG&OPR_NODE_OP_YBL/HC_CONFIG_OP.csv
	output_dir=/mnt/c/Users/ADanej/OneDrive\ -\ Micro\ Focus/cases/Yes\ Bank/obm\ oa\ health\ report/HC_CONFIG&OPR_NODE_OP_YBL

	#2 WORKS
	#opr_node_file=/mnt/c/Temp/OPR_NODE_OP.csv
	#opr_hc_file=/mnt/c/Temp/HC_CONFIG_OP.csv
	#output_dir=/mnt/c/Temp/
```
	
## 3 Dev: Passing arguments in CLI or via external properties file in Windows CMD/Powershell

### 3a CMD with CLI Arguments

```
java -jar target/ybl-oastatusreport-spring-boot-0.0.1-SNAPSHOT.jar \
--opr_node_file="C:\\Users\\ADanej\\OneDrive - Micro Focus\\cases\\Yes Bank\\obm oa health report\\HC_CONFIG&OPR_NODE_OP_YBL\\OPR_NODE_OP.csv" \
--opr_hc_file="C:\\Users\\ADanej\\OneDrive - Micro Focus\\cases\\Yes Bank\\obm oa health report\\HC_CONFIG&OPR_NODE_OP_YBL\\HC_CONFIG_OP.csv" \
--output_dir="C:\\Users\\ADanej\\OneDrive - Micro Focus\\cases\\Yes Bank\\obm oa health report\\HC_CONFIG&OPR_NODE_OP_YBL"
```

### 3b CMD with external props file
`C:\java\jdk-11.0.2\bin\java -jar target/ybl-oastatusreport-spring-boot-0.0.1-SNAPSHOT.jar --spring.config.location="C:\\Users\\ADanej\\Documents\\My Stuff\\workspace_sts4\\ybl-oastatusreport-spring-boot\\config.properties"`

Properties file entry samples, with and without spaces in file path.
	
```
	#3 WORKS with CMD
	#opr_node_file=C:\\Users\\ADanej\\OneDrive - Micro Focus\\cases\\Yes Bank\\obm oa health report\\HC_CONFIG&OPR_NODE_OP_YBL\\OPR_NODE_OP.csv
	#opr_hc_file=C:\\Users\\ADanej\\OneDrive - Micro Focus\\cases\\Yes Bank\\obm oa health report\\HC_CONFIG&OPR_NODE_OP_YBL\\HC_CONFIG_OP.csv
	#output_dir=C:\\Users\\ADanej\\OneDrive - Micro Focus\\cases\\Yes Bank\\obm oa health report\\HC_CONFIG&OPR_NODE_OP_YBL
	# 4 WORKS with CMD
	opr_node_file=c:\\Temp\\OPR_NODE_OP.csv
	opr_hc_file=c:\\Temp\\HC_CONFIG_OP.csv
	output_dir=c:\\Temp
```

### 4 Build an executable app using jpackage utility

I am using Java 16 for this.

```
jpackage --input package \
	--type app-image 
	--main-jar ybl-oastatusreport-spring-boot-0.0.2-SNAPSHOT.jar
```

The jpackage command has dependencies where I am developing the app (in WSL Ubuntu image). These two commands install the pre-reqs.

```
apt-get install -y fakeroot
apt-get install -y binutils
```

After using mvn clean package, I have moved the JAR file into a new directory called 'package'. The jpackage command produces a below structure in the directory where jpackage was run. It includes the JRE, so the target Debian machine does not require JRE to run it.

```
JarLauncher/
├── bin
│   └── JarLauncher <== this is the executable
└── lib
    ├── JarLauncher.png
    ├── app
    │   ├── JarLauncher.cfg
    │   └── ybl-oastatusreport-spring-boot-0.0.2-SNAPSHOT.jar
    └── runtime < many files here >
```

Command to run the executable (Ubuntu)

```
root@INADanej01:/mnt/c/Users/ADanej/Documents/My Stuff/workspace_sts4/ybl-oastatusreport-spring-boot# ./JarLauncher/bin/JarLauncher --spring.config.location=/mnt/c/Users/ADanej/Documents/My\ Stuff/workspace_sts4/ybl-oastatusreport-spring-boot/config.properties
```