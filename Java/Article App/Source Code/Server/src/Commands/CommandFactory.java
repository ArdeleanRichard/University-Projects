package Commands;

public class CommandFactory {

	public static Command getCommand(String[] args) {
		try {
			
			switch (args[0]) {
			case "login":
				return new LoginCommand(args[1], args[2]);
			case "read":
				return new ReadCommand();
			case "write":
				return new WriteCommand(args[1], args[2], args[3], args[4], args[5]);
			case "createWriter":
				return new CreateWriterCommand(args[1], args[2]);
			case "deleteWriter":
				return new DeleteWriterCommand(args[1]);
			case "updateWriter":
				return new UpdateWriterCommand(args[1], args[2]);
			default:
				return null;
			}
		} 
		catch (ArrayIndexOutOfBoundsException arrE) {
			return null;
		}

	}

}
