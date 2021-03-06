package saf.ui;

import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Pane;
import javafx.stage.Screen;
import javafx.stage.Stage;
import properties_manager.PropertiesManager;
import saf.controller.AppFileController;
import saf.AppTemplate;
import static saf.settings.AppPropertyType.*;
import static saf.settings.AppStartupConstants.FILE_PROTOCOL;
import static saf.settings.AppStartupConstants.PATH_IMAGES;
import saf.components.AppStyleArbiter;
import saf.components.AppWorkspaceComponent;

/**
 * This class provides the basic user interface for this application, including
 * all the file controls, but not including the workspace, which would be
 * customly provided for each app.
 *
 * @author Richard McKenna
 * @author ?
 * @version 1.0
 */
public class AppGUI implements AppStyleArbiter {
    // THIS HANDLES INTERACTIONS WITH FILE-RELATED CONTROLS

    /**
     *
     */
    protected AppFileController fileController;

    // THIS IS THE APPLICATION WINDOW
    /**
     *
     */
    protected Stage primaryStage;

    // THIS IS THE STAGE'S SCENE GRAPH
    /**
     *
     */
    protected Scene primaryScene;

    // THIS PANE ORGANIZES THE BIG PICTURE CONTAINERS FOR THE
    // APPLICATION AppGUI
    /**
     *
     */
    protected BorderPane appPane;
    protected BorderPane toolPane;

    // THIS IS THE TOP TOOLBAR AND ITS CONTROLS
    /**
     *
     */
    protected FlowPane fileToolbarPane;
    protected FlowPane editToolbarPane;
    protected FlowPane freePane;

    /**
     *
     */
    protected Button newButton;
    protected Label thickness;
    protected Label zoom;
    protected Label filler;

    /**
     *
     */
    protected Button loadButton;

    /**
     *
     */
    protected Button saveButton;

    /**
     *
     */
    //file toolbar
    protected Button exitButton;
    protected Button newMapButton;
    protected Button saveMapButton;
    protected Button exportMapButton;

    // edit toolbar
    protected ColorPicker changeBackgroundColorButton;
    protected ColorPicker borderColorButton;
    protected Slider borderThicknessSlider;
    protected Slider zoomSlider;
    protected Button reassignColorsButton;
    protected Button renameMapButton;
    protected Button addImageButton;
    protected Button removeButton;
    protected Button playButton;
    protected Button resizeButton;

    // HERE ARE OUR DIALOGS
    /**
     *
     */
    protected AppYesNoCancelDialogSingleton yesNoCancelDialog;

    /**
     *
     */
    protected String appTitle;

    /**
     * This constructor initializes the file toolbar for use.
     *
     * @param initPrimaryStage The window for this application.
     *
     * @param initAppTitle The title of this application, which will appear in
     * the window bar.
     *
     * @param app The app within this gui is used.
     */
    public AppGUI(Stage initPrimaryStage,
            String initAppTitle,
            AppTemplate app) {
        // SAVE THESE FOR LATER
        primaryStage = initPrimaryStage;
        appTitle = initAppTitle;

        // INIT THE TOOLBAR
        toolPane = new BorderPane();
        freePane = new FlowPane();
        initFileToolbar(app);
        initEditToolbar(app);

        // AND FINALLY START UP THE WINDOW (WITHOUT THE WORKSPACE)
        initWindow();

    }

    /**
     * Accessor method for getting the application pane, within which all user
     * interface controls are ultimately placed.
     *
     * @return This application GUI's app pane.
     */
    public BorderPane getAppPane() {
        return appPane;
    }

    /**
     * Accessor method for getting this application's primary stage's, scene.
     *
     * @return This application's window's scene.
     */
    public Scene getPrimaryScene() {
        return primaryScene;
    }

    /**
     * Accessor method for getting this application's window, which is the
     * primary stage within which the full GUI will be placed.
     *
     * @return This application's primary stage (i.e. window).
     */
    public Stage getWindow() {
        return primaryStage;
    }

    public ColorPicker getColorButton() {
        return changeBackgroundColorButton;
    }

    public Button getSaveButton() {
        return saveButton;
    }

    public Button getExportButton() {
        return exportMapButton;
    }

    public Button getNewButton() {
        return newButton;
    }

    /**
     * This method is used to activate/deactivate toolbar buttons when they can
     * and cannot be used so as to provide foolproof design.
     *
     * @param saved Describes whether the loaded Page has been saved or not.
     */
    public void updateToolbarControls(boolean saved) {
        // THIS TOGGLES WITH WHETHER THE CURRENT COURSE
        // HAS BEEN SAVED OR NOT
        saveButton.setDisable(saved);

        // ALL THE OTHER BUTTONS ARE ALWAYS ENABLED
        // ONCE EDITING THAT FIRST COURSE BEGINS
        newButton.setDisable(false);
        loadButton.setDisable(false);
        exitButton.setDisable(false);

        // NOTE THAT THE NEW, LOAD, AND EXIT BUTTONS
        // ARE NEVER DISABLED SO WE NEVER HAVE TO TOUCH THEM
    }

    /**
     * *************************************************************************
     */
    /* BELOW ARE ALL THE PRIVATE HELPER METHODS WE USE FOR INITIALIZING OUR AppGUI */
    /**
     * *************************************************************************
     */
    /**
     * This function initializes all the buttons in the toolbar at the top of
     * the application window. These are related to file management.
     */
    private void initFileToolbar(AppTemplate app) {
        fileToolbarPane = new FlowPane();

        freePane.setAlignment(Pos.CENTER);
        // HERE ARE OUR FILE TOOLBAR BUTTONS, NOTE THAT SOME WILL
        // START AS ENABLED (false), WHILE OTHERS DISABLED (true)
        //@todo change strings
        newButton = initChildButton(fileToolbarPane, NEW_ICON.toString(), NEW_TOOLTIP.toString(), false);
        loadButton = initChildButton(fileToolbarPane, LOAD_ICON.toString(), LOAD_TOOLTIP.toString(), false);
        saveButton = initChildButton(fileToolbarPane, SAVE_ICON.toString(), SAVE_TOOLTIP.toString(), true);
        exportMapButton = initChildButton(fileToolbarPane, EXPORT_ICON.toString(), EXPORT_TOOLTIP.toString(), true);

        exitButton = initChildButton(fileToolbarPane, EXIT_ICON.toString(), EXIT_TOOLTIP.toString(), false);

        // AND NOW SETUP THEIR EVENT HANDLERS
        fileController = new AppFileController(app);
        exitButton.setOnAction(e -> {
            fileController.handleExitRequest();
        });
        toolPane.setLeft(fileToolbarPane);
    }

    public Button getOpenButton() {
        return loadButton;
    }

    public FlowPane getFreePane() {
        return freePane;
    }

    public Button getRenameButton() {
        return renameMapButton;
    }

    public Button getAddButton() {
        return addImageButton;
    }

    public Button getRemoveButton() {
        return removeButton;
    }

    public ColorPicker getBackgroundButton() {
        return changeBackgroundColorButton;
    }

    public ColorPicker getBorderColorButton() {
        return borderColorButton;
    }

    private void initEditToolbar(AppTemplate app) {

        editToolbarPane = new FlowPane();
        // editToolbarPane.setAlignment(Pos.CENTER);
        renameMapButton = initChildButton(editToolbarPane, CHANGE_NAME.toString(), CHANGE_TOOLTIP.toString(), true);
        addImageButton = initChildButton(editToolbarPane, ADD_ICON.toString(), ADD_TOOLTIP.toString(), true);
        removeButton = initChildButton(editToolbarPane, REMOVE.toString(), REMOVE_TOOLTIP.toString(), true);
        PropertiesManager props = PropertiesManager.getPropertiesManager();
        changeBackgroundColorButton = new ColorPicker();

        Tooltip buttonTooltip = new Tooltip(props.getProperty(CHANGE_COLOR_TOOLTIP.toString()));
        changeBackgroundColorButton.setTooltip(buttonTooltip);
        changeBackgroundColorButton.setDisable(true);
        Label backLabel = new Label("Background Color",changeBackgroundColorButton);
        backLabel.setContentDisplay(ContentDisplay.TOP);
        editToolbarPane.getChildren().add(changeBackgroundColorButton);
        editToolbarPane.getChildren().add(backLabel);
        borderColorButton = new ColorPicker();
        Tooltip borderTip = new Tooltip(props.getProperty(BORDER_COLOR_TOOLTIP.toString()));
        borderColorButton.setTooltip(borderTip);
        Label borderLabel = new Label("Borer Color", borderColorButton);
        borderLabel.setContentDisplay(ContentDisplay.TOP);
        borderColorButton.setDisable(true);
        editToolbarPane.getChildren().add(borderColorButton);
        editToolbarPane.getChildren().add(borderLabel);
        borderThicknessSlider = new Slider();
        thickness = new Label(props.getProperty(THICKNESS_TOOLTIP), borderThicknessSlider);
        borderThicknessSlider.setDisable(true);
        thickness.setContentDisplay(ContentDisplay.TOP);
        editToolbarPane.getChildren().add(thickness);
        editToolbarPane.getChildren().add(borderThicknessSlider);
        
        zoomSlider = new Slider();
        zoomSlider.setDisable(true);
        zoom = new Label(props.getProperty(ZOOM_TOOLTIP), zoomSlider);
        zoom.setContentDisplay(ContentDisplay.TOP);
        editToolbarPane.getChildren().add(zoomSlider);
        editToolbarPane.getChildren().add(zoom);
        reassignColorsButton = initChildButton(editToolbarPane, REASSIGN_ICON.toString(), REASSIGN_COLOR_TOOLTIP.toString(), true);
        playButton = initChildButton(editToolbarPane, PLAY_ICON.toString(), PLAY_TOOLTIP.toString(), true);
        resizeButton = initChildButton(editToolbarPane, RESIZE_ICON.toString(), RESIZE_TOOLTIP.toString(), true);
        toolPane.setRight(freePane);
        toolPane.setCenter(editToolbarPane);

    }

    public Button getResizeButton() {
        return resizeButton;
    }

    public Button getReassignButton() {
        return reassignColorsButton;
    }

    public Button getPlayButton() {
        return playButton;
    }

    public Slider getZoomSlider() {
        return zoomSlider;
    }

    public Slider getThickness() {
        return borderThicknessSlider;
    }

    // INITIALIZE THE WINDOW (i.e. STAGE) PUTTING ALL THE CONTROLS
    // THERE EXCEPT THE WORKSPACE, WHICH WILL BE ADDED THE FIRST
    // TIME A NEW Page IS CREATED OR LOADED
    private void initWindow() {
        // SET THE WINDOW TITLE
        primaryStage.setTitle(appTitle);

        // GET THE SIZE OF THE SCREEN
        Screen screen = Screen.getPrimary();
        Rectangle2D bounds = screen.getVisualBounds();

        // AND USE IT TO SIZE THE WINDOW
        primaryStage.setX(bounds.getMinX());
        primaryStage.setY(bounds.getMinY());
        primaryStage.setWidth(bounds.getWidth());
        primaryStage.setHeight(bounds.getHeight());

        // ADD THE TOOLBAR ONLY, NOTE THAT THE WORKSPACE
        // HAS BEEN CONSTRUCTED, BUT WON'T BE ADDED UNTIL
        // THE USER STARTS EDITING A COURSE
        appPane = new BorderPane();
        appPane.setTop(toolPane);

        primaryScene = new Scene(appPane);

        // SET THE APP ICON
        PropertiesManager props = PropertiesManager.getPropertiesManager();
        String appIcon = FILE_PROTOCOL + PATH_IMAGES + props.getProperty(APP_LOGO);
        primaryStage.getIcons().add(new Image(appIcon));

        // NOW TIE THE SCENE TO THE WINDOW AND OPEN THE WINDOW
        primaryStage.setScene(primaryScene);
        primaryStage.show();
    }

    /**
     * This is a public helper method for initializing a simple button with an
     * icon and tooltip and placing it into a toolbar.
     *
     * @param toolbar Toolbar pane into which to place this button.
     *
     * @param icon Icon image file name for the button.
     *
     * @param tooltip Tooltip to appear when the user mouses over the button.
     *
     * @param disabled true if the button is to start off disabled, false
     * otherwise.
     *
     * @return A constructed, fully initialized button placed into its
     * appropriate pane container.
     */
    public Button initChildButton(Pane toolbar, String icon, String tooltip, boolean disabled) {
        PropertiesManager props = PropertiesManager.getPropertiesManager();

        // LOAD THE ICON FROM THE PROVIDED FILE
        String imagePath = FILE_PROTOCOL + PATH_IMAGES + props.getProperty(icon);
        Image buttonImage = new Image(imagePath);

        // NOW MAKE THE BUTTON
        Button button = new Button();
        button.setDisable(disabled);
        ImageView btnimg = new ImageView(buttonImage);
        btnimg.setFitHeight(18);
        btnimg.setFitWidth(18);
        button.setGraphic(btnimg);

        Tooltip buttonTooltip = new Tooltip(props.getProperty(tooltip));
        button.setTooltip(buttonTooltip);

        // PUT THE BUTTON IN THE TOOLBAR
        toolbar.getChildren().add(button);

        // AND RETURN THE COMPLETED BUTTON
        return button;
    }

    /**
     * This function specifies the CSS style classes for the controls managed by
     * this framework.
     */
    @Override
    public void initStyle() {
        fileToolbarPane.getStyleClass().add(CLASS_BORDERED_PANE);
        editToolbarPane.getStyleClass().add(CLASS_BORDERED_PANE);
        freePane.getStyleClass().add(CLASS_BORDERED_PANE);
        newButton.getStyleClass().add(CLASS_FILE_BUTTON);
        loadButton.getStyleClass().add(CLASS_FILE_BUTTON);
        saveButton.getStyleClass().add(CLASS_FILE_BUTTON);
        exitButton.getStyleClass().add(CLASS_FILE_BUTTON);
    }
}
