# karaf-shell-tui

Integration of Tamboui libraries into the Karaf shell.

[![Watch the video](https://avatars.githubusercontent.com/u/8010769?v=4)](https://www.youtube.com/watch?v=b4JmRQJIpn4)

## Maven Central release

This repository is configured to publish release artifacts to Maven Central
through the Sonatype Central Portal Maven plugin.

### Requirements

1. A claimed namespace for the published coordinates.
2. A GPG key available to Maven.
3. A Central Portal user token stored in `~/.m2/settings.xml`:

```xml
<settings>
  <servers>
    <server>
      <id>central</id>
      <username>${env.CENTRAL_TOKEN_USERNAME}</username>
      <password>${env.CENTRAL_TOKEN_PASSWORD}</password>
    </server>
  </servers>
</settings>
```

### Commands

Create the publication bundle locally without uploading it:

```bash
mvn -Prelease -Dgpg.skip -Dcentral.publishing.skipPublishing=true clean deploy
```

Upload the release bundle for validation in Maven Central:

```bash
mvn -Prelease clean deploy
```

By default the deployment is uploaded and validated, but not auto-published.
After validation, publish it from the Sonatype Central Portal.
