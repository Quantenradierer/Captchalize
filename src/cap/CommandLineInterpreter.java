package cap;

import cap.ArgumentParser;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.OptionBuilder;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

public class CommandLineInterpreter {

    private ArgumentParser parser = new ArgumentParser();

    public CommandLineInterpreter() {
        this.createDefaultOptions();
    }

    public CommandLineInterpreter(ArgumentParser parser) {
        this.parser = parser;
        this.createDefaultOptions();
    }

    public void run(String[] arguments) {
        try {
            this.interpret(this.parser.parse(arguments));
        } catch (ParseException exception) {
            this.parser.printHelp();
        }
    }

    protected void createDefaultOptions() {
        Option serverMode = OptionBuilder
            .withLongOpt("server-mode")
            .withDescription("running in server mode.")
            .create("s");

        Option parseMode = OptionBuilder
            .withLongOpt("find")
            .withDescription("find and analyze a CAPTCHA from a website.")
            .hasArg()
            .withArgName("url")
            .create("f");

        Option captchaSystem = OptionBuilder
            .withLongOpt("captcha-system")
            .withDescription("use a specific CAPTCHA system.")
            .hasArg()
            .withArgName("system_name")
            .create("y");

        Option debugMode = OptionBuilder
            .withLongOpt("debug")
            .withDescription("print step by step results.")
            .create();

        Option debugGUIMode = OptionBuilder
            .withLongOpt("debug-gui")
            .withDescription("offer a gui for step by step results.")
            .create();

        Option help = OptionBuilder
            .withLongOpt("help")
            .withDescription("print this message.")
            .create();

        Options op = this.parser.getOptions();

        op.addOption(serverMode);
        op.addOption(parseMode);
        op.addOption(captchaSystem);
        op.addOption(debugMode);
        op.addOption(debugGUIMode);
        op.addOption(help);

        this.parser.setOptions(op);
    }

    protected void interpret(CommandLine line) {
        if (line.hasOption("help")) {
            this.parser.printHelp();
            return;
        }

        if (line.hasOption("debug")) {

        }

        if (line.hasOption("debug-gui")) {
            
        }

        if (line.hasOption("captcha-system")) {
            System.out.println(line.getOptionValue("captcha-system"));
        }

        if (line.hasOption("server-mode")) {
            return;
        } else if (line.hasOption("find")) {
            System.out.println(line.getOptionValue("find"));
            return;
        }

        for (String imagePath : line.getArgs()) {
            System.out.println(imagePath);
        }
    }
}