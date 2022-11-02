Plugin for IDE PyCharm that analyzes project and calculates The Main Sequence

version - 1.1 beta

<h2>How to use</h2>

<h3>How to open GUI</h3>
1. Wait until your IDE will ready for working
2. Open "CleanArch" tab in the tool window bar (right side of IDE)
3. Plugin's GUI will open, and you will see your project's Main Sequence

<h3>How to get I and A of the file</h3>
1. Open any file in your project
2. You'll see notification with I (Stability) and A (Abstraction)

<h4>About GUI</h4>
1. The white circles is all your python files in the project
2. The light green circle is your opened python file
3. If you hover any circle then the GUI will show perpendiculars from current circle to the OX and OY axis
4. If you click on the circle then PyCharm will open this circle's file

<h2>How to download</h2>

1. Download any .jar file in the "jar" folder (the latest version is recommended (CleanArch-1.2-beta.jar))
2. Open your IDE PyCharm
3. Go to settings (Ctrl + Alt + S (Windows/Linux), ⌘ (MacOS))
4. Open plugin menu
5. Click to the settings icon after "Installed" tab
6. Choose "Install plugin from disk..."
7. Choose downloaded .jar file
8. Press accept

You downloaded this plugin

<h2>Versions info</h2>
1. <h4>PycharmPlugin-1.0.0-beta.jar</h4>
    - The first version of plugin
    - The first version of GUI (**Ctrl + Alt + \\** then **Ctrl + Alt + /** to invoke)
    - GUI is not adaptive
    - Not shows I and A of opened file when file opening in the editor
    - Have enough bugs
2. <h4>PycharmPlugin-1.0.1-beta</h4>
   - Some bugs from first version fixed
   - Still bad GUI
3. <h4>PycharmPlugin-1.1-beta</h4>
    - The second version of GUI (**Alt + C** then **Alt + A** to invoke)
    - GUI became adaptive
    - Shows I and A of opened file when file opening in the editor in notifications
4. <h4>CleanArch-1.2-beta</h4>
    - The third version of GUI (tool window bar (right side of IDE))
    - Opened sources of the plugin
    - Updated notification when user opens python file
    - Bugs fixed