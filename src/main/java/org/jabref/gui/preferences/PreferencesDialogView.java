package org.jabref.gui.preferences;

import java.util.Locale;

import javax.inject.Inject;

import javafx.fxml.FXML;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ListView;
import javafx.scene.control.ScrollPane;

import org.jabref.gui.DialogService;
import org.jabref.gui.JabRefFrame;
import org.jabref.gui.icon.IconTheme;
import org.jabref.gui.theme.ThemeManager;
import org.jabref.gui.util.BaseDialog;
import org.jabref.gui.util.ControlHelper;
import org.jabref.gui.util.ViewModelListCellFactory;
import org.jabref.logic.l10n.Localization;
import org.jabref.preferences.PreferencesService;

import com.airhacks.afterburner.views.ViewLoader;
import com.tobiasdiez.easybind.EasyBind;
import org.controlsfx.control.textfield.CustomTextField;

/**
 * Preferences dialog. Contains a TabbedPane, and tabs will be defined in separate classes. Tabs MUST implement the
 * PreferencesTab interface, since this dialog will call the storeSettings() method of all tabs when the user presses
 * ok.
 */
public class PreferencesDialogView extends BaseDialog<PreferencesDialogViewModel> {

    @FXML private CustomTextField searchBox;
    @FXML private ListView<PreferencesTab> preferenceTabList;
    @FXML private ScrollPane preferencesContainer;
    @FXML private ButtonType saveButton;

    @Inject private DialogService dialogService;
    @Inject private PreferencesService preferencesService;
    @Inject private ThemeManager themeManager;

    private final JabRefFrame frame;
    private PreferencesDialogViewModel viewModel;

    public PreferencesDialogView(JabRefFrame frame) {
        this.frame = frame;
        this.setTitle(Localization.lang("JabRef preferences"));

        ViewLoader.view(this)
                  .load()
                  .setAsDialogPane(this);

        ControlHelper.setAction(saveButton, getDialogPane(), event -> savePreferencesAndCloseDialog());

        themeManager.updateFontStyle(getDialogPane().getScene());
    }

    public PreferencesDialogViewModel getViewModel() {
        return viewModel;
    }

    @FXML
    private void initialize() {
        viewModel = new PreferencesDialogViewModel(dialogService, preferencesService, frame);

        preferenceTabList.itemsProperty().setValue(viewModel.getPreferenceTabs());

        PreferencesSearchHandler searchHandler = new PreferencesSearchHandler(viewModel.getPreferenceTabs());
        preferenceTabList.itemsProperty().bindBidirectional(searchHandler.filteredPreferenceTabsProperty());
        searchBox.textProperty().addListener((observable, previousText, newText) -> {
            searchHandler.filterTabs(newText.toLowerCase(Locale.ROOT));
            preferenceTabList.getSelectionModel().clearSelection();
            preferenceTabList.getSelectionModel().selectFirst();
        });
        searchBox.setPromptText(Localization.lang("Search") + "...");
        searchBox.setLeft(IconTheme.JabRefIcons.SEARCH.getGraphicNode());

        EasyBind.subscribe(preferenceTabList.getSelectionModel().selectedItemProperty(), tab -> {
            if (tab instanceof AbstractPreferenceTabView<?> preferencesTab) {
                preferencesContainer.setContent(preferencesTab.getBuilder());
                preferencesTab.prefWidthProperty().bind(preferencesContainer.widthProperty());
                preferencesTab.getStyleClass().add("preferencesTab");
            } else {
                preferencesContainer.setContent(null);
            }
        });

        preferenceTabList.getSelectionModel().selectFirst();
        new ViewModelListCellFactory<PreferencesTab>()
                .withText(PreferencesTab::getTabName)
                .install(preferenceTabList);

        viewModel.setValues();
    }

    @FXML
    private void closeDialog() {
        close();
    }

    @FXML
    private void savePreferencesAndCloseDialog() {
        if (viewModel.validSettings()) {
            viewModel.storeAllSettings();
            closeDialog();
        }
    }

    @FXML
    void exportPreferences() {
        viewModel.exportPreferences();
    }

    @FXML
    void importPreferences() {
        viewModel.importPreferences();
    }

    @FXML
    void showAllPreferences() {
        viewModel.showPreferences();
    }

    @FXML
    void resetPreferences() {
        viewModel.resetPreferences();
    }
}
