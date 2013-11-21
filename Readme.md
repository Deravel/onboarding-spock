First, read [this document](https://code.google.com/p/spock/wiki/SpockBasics) to get a general overview of the Spock framework.

Next, remove all answers by executing the following code...

    ./gradlew removeSolutions

At this point, all the *Spock tests in the following packages should fail.

    com.example.basics
    com.example.interaction
    com.example.exception

For each package above (in order), go through the various tests in numeric order, filling in code to make the tests pass.

    // SNIPPET START
    <insert code here>
    // SNIPPET END

For most *Spock classes there is a corresponding *Mockito class which serves as a comparison between spock and mockito
and also demonstrates intent for each test.  If you're having trouble with filling in a test, look at the corresponding
mockito test for an example.

If you need assistance along the way, see spock [documentation](http://docs.spockframework.org/en/latest)

After running through the basics, have a look at

    com.example.datadriven
    com.example.gotchas

which provide a brief overview of data-driven tests in Spock, as well as some non-obvious behavior to be aware of.