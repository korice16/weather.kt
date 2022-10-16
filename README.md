# weather.kt

## Development Workflow 
### Branches 

First, let's see the different types of branch used in the developement worklow with their permissions for a user with the `Developer` role.

| Branches      |      Allow To Push       |      Allow To Merge      |     Allow Push Force     |
| ------------- | :----------------------: | :----------------------: | :----------------------: |
| __master__    | :heavy_multiplication_x: | :heavy_multiplication_x: | :heavy_multiplication_x: |
| __release__   | :heavy_multiplication_x: | :heavy_multiplication_x: | :heavy_multiplication_x: |
| __develop__   | :heavy_multiplication_x: | :heavy_multiplication_x: | :heavy_multiplication_x: |
| __feature/*__ |    :heavy_check_mark:    |    :heavy_check_mark:    |    :heavy_check_mark:    |

### Commits Convention

The Conventional Commits specification is a lightweight convention on top of commit messages. It provides an easy set of rules for creating an explicit commit history. This convention gives details by describing the features, fixes, and breaking changes made in commit messages

The commit message should be structured as follows:
```
<type>[optional scope]: <description>

[optional body]

[optional footer(s)]
```
The `<type>` tag can be filled using `init:`, `fix:`, `feat:`, `ci:`, `docs:`, `style:`, `refactor:`, `perf:`, `test:`.

For more information about the convention, check the specification on [Convetional Commits](https://www.conventionalcommits.org/en/v1.0.0/#specification).

### Workflow
#### Create a new feature
Always get the latest version of the `develop` branch before starting a feature!
```Bash
git checkout develop                # If not already on develop
git pull origin develop             # Pull the latest version of develop
```

We can now create our new feature branch.
```Bash
git checkout -b feature/helloworld  # Create and checkout on the feature/helloworld branch
```

**__Also, it's important to note that you always need to write unit tests related to your feature. Before pushing your feature branch, always execute unit tests and check your code format using a linter.__**
```Bash
./gradlew check                     # Non-regression tests
./gradlew lint                      # Code format checking
```

Then you can push your branch using the command below.
```Bash
git push -u origin helloworld       # Push your branch while setting the up stream
```

When you finished your work on the feature, you can create a new `Pull Request` to add your work on the `develop` branch.
If your `Pull Request` is blocked because of conflicts, you will have to rebase your branch on the latest version of `develop`.
```Bash
git checkout develop                # If not already on develop
git pull origin develop             # Pull the latest version of develop
git checkout feature/helloworld     # Go back on your feature branch
git rebase -i develop               # Rebase your feature branch on develop and fix your conflicts
git push -f                         # Force push your rebased branch
```

Your `Pull Request` will then be reviewed before being merged in the `develop` branch.
