# Templie

Simple template engine written in Java

## Reference

```java
import com.ajlopez.templie;
```

## Usage

```java
// Compile the template
Template template = Template.compile("Hello, ${name}!");

// Setup the model
Map<String, Object> model = new HashMap<String, Object>();
model.put("name", "World");

// Run the template with model
String result = template.run(model); // "Hello, World!"
```

## Samples

TBD

## Versions

- 0.0.1: Replace variables

## License

MIT

## References

- [How to see if an object is an array?](http://stackoverflow.com/questions/2725533/how-to-see-if-an-object-is-an-array)

## Contribution

Feel free to [file issues](https://github.com/ajlopez/Templie) and submit
[pull requests](https://github.com/ajlopez/Templie/pulls) � contributions are
welcome<

If you submit a pull request, please be sure to add or update corresponding
test cases, and ensure that `npm test` continues to pass.

