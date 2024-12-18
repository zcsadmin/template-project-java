
# Madness Lab's Template Project Java

You can use this project as a template to start the development of your new Java application.

## Run the application

You can start the application by running the following command:

```bash
./run.sh
```

## Debug with VsCode

Create a file `.vscode/launch.json` with the following content:

```json
{
    "version": "0.2.0",
    "configurations": [
        {
            "type": "java",
            "name": "Attach debug",
            "request": "attach",
            "hostName": "localhost",
            "port": "5005"
        }
    ]
}
```

Run with `./run.sh -d`

## Notes

Here are some example commands:

```bash
mvn clean test
mvn versions:display-dependency-updates
mvn versions:display-plugin-updates
mvn clean package
mvn exec:java
```

## Support

[Claudio Cavina](mailto:c.cavina@zcscompany.com)  
[Michele Mondelli](mailto:m.mondelli@zcscompany.com)
