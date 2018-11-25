# file_renamer
A tool that renames files in predefined directories, based on given names and file extension.

# Usage:

1) Download the `file_renamer.jar` file from this page.
2) Create a folder on your desktop named `FileRenamerFiles`.
3) Create two directories inside it, named `Entity` and `Map`.
3) Open the command line.
4) Execute the jar with `java -jar file_renamer.jar [parameters]` (see below).

# Parameters

First parameter: File extension - e.g. .java, .cs.
Rest parameters: File name pairs (before=after) to be renamed- e.g. Customers=Customer, Sales=Sale.

You can also execute with parameter `help` to view basic instructions.

e.g. `java -jar file_renamer.jar help`

# Sample

Sample input: `java -jar file_renamer.jar .java File=Dog Person=Cat`  
Sample output:

### BEFORE 
```
C:\Users\User\Desktop\FileRenamerFiles
├───Entity
│       File.java
│       Person.java
│
└───Map
        FileMap.java
        PersonMap.java
```
```
public class File {
    private int age;
    private String name;
}
```
```
public class Person {
    private int age;
    private String name;
}
```
```
public class FileMap extends ClassMap<File> {
    public FileMap() {

    }
}
```
```
public class PersonMap extends ClassMap<Person> {
    public PersonMap() {

    }
}
```


### AFTER
```
C:\Users\User\Desktop\FileRenamerFiles
├───Entity
│       Cat.java
│       Dog.java
│
└───Map
        CatMap.java
        DogMap.java
```
```
public class Cat {
    private int age;
    private String name;
}
```
```
public class Dog {
    private int age;
    private String name;
}
```
```
public class CatMap extends ClassMap<Cat> {
    public CatMap() {

    }
}
```
```
public class DogMap extends ClassMap<Dog> {
    public DogMap() {

    }
}
```

Thank you for using File Renamer.
