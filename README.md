
# Madness Lab's Template Project Java

This is a standard Java template used to start the development of new ZCS (Zucchetti Centro Sistemi) applications.

You can use this project as a template to start the development of your new Java application.

Related resources:

- [ZCS Java Docker images](https://hub.docker.com/r/zcscompany/java) — Docker Hub repository
- [docker-image-java](https://github.com/zcsadmin/docker-image-java) — GitHub repository for the ZCS Java Docker images
- [template-project-python](https://github.com/zcsadmin/template-project-python) — ZCS Python template project

## How to start

Follow these steps to bootstrap a new project from this template.

### 1. Clone the template

```bash
git clone git@github.com:zcsadmin/template-project-java.git my-new-project
cd my-new-project
```

### 2. Reset git and add a custom origin

Remove the template git history and initialize a fresh repository:

```bash
rm -rf .git
git init
git add .
git commit -m "Initial commit"
```

Then point it to your own remote:

```bash
git remote add origin git@github.com:zcsadmin/my-new-project.git
git push -u origin main
```

### 3. Change the project placeholders

Replace the template-specific values with your own:

- `app/pom.xml`: `groupId`, `artifactId`, `name` and `description` (Maven coordinates)
- Java base package `dev.zcscloud.ml.tpj.app` → your own package (update the folders under `app/src/main/java` and `app/src/test/java`)
- `docker-compose.yml`: `APP_NAME` (e.g. `my-new-project`)
- This README: title and description

### 4. Start the new project

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

## License

This code is released under the MIT License.

## Support

This code has been developed and released by Laboratorio della Follia, a R&D division of Zucchetti Centro Sistemi.

For support contact [Michele Mondelli](mailto:m.mondelli@zcscompany.com) or [Claudio Cavina](mailto:c.cavina@zcscompany.com).
