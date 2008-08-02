package info.rvin.flexmojo.test;

import java.io.File;
import info.rvin.flexmojos.utilities.MavenUtils;

/**
 * This class is used to launch the FlexUnit tests.
 */
public class FlexUnitLauncher {
	private static final String WINDOWS_CMD = "rundll32 url.dll,FileProtocolHandler ";
	private static final String MAC_CMD = "open -g ";
	private static final String UNIX_CMD = "gflashplayer ";

	/**
	 * Run the SWF that contains the FlexUnit tests.
	 * 
	 * @param swf
	 *            the SWF.
	 * @throws Exception
	 *             if there is an error launching the tests.
	 */
	public void runTests(File swf) throws Exception {
		if (MavenUtils.isWindows()) {
			// Ideally we want to launch the SWF in the player so we can close
			// it, not so easy in a browser. We let 'rundll32' do the work based
			// on the extension of the file passed in.
			Runtime.getRuntime().exec(WINDOWS_CMD + swf.getAbsolutePath());
		} else if (MavenUtils.isMac()) {
			// Ideally we want to launch the SWF in the player so we can close
			// it, not so easy in a browser. We let 'open' do the work based
			// on the extension of the file passed in.
			Runtime.getRuntime().exec(MAC_CMD + swf.getAbsolutePath());
		} else {
			// If we are running in UNIX the fallback is to the browser. To do
			// this Netscape must be running for the "-remote" flag to work. If
			// the browser is not running we need to start it.
			Process p = Runtime.getRuntime().exec(
					UNIX_CMD + swf.getAbsolutePath());

			// If the exist code is '0', then the browser was running, otherwise
			// we need to start the browser.
			int exitValue = p.waitFor();

			if (exitValue != 0) {
				Runtime.getRuntime().exec(UNIX_CMD + swf.getAbsolutePath());
			}
		}
	}

}