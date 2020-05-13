package com.encryption.application.views.dashboard;

import com.encryption.application.backend.BackendService;
import com.encryption.application.backend.User;
import com.encryption.application.crypto.AESCrypto;
import com.encryption.application.crypto.Crypto;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.select.Select;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.binder.ValidationException;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouteAlias;
import com.encryption.application.views.main.MainView;
import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.binary.Hex;
import java.util.logging.Logger;

import java.util.Random;

@Route(value = "dashboard", layout = MainView.class)
@RouteAlias(value = "", layout = MainView.class)
@PageTitle("Dashboard")
@CssImport("styles/views/dashboard/dashboard-view.css")
public class DashboardView extends Div {

    private User user = new User("", "", "", "PKCS5PADDING", "CBC");
    private BackendService bs = new BackendService();
    private TextField key = new TextField();
    private TextField IV = new TextField();
    private TextField cipherText = new TextField();
    private Select<String> padding = new Select<>();
    private Select<String> algorithm = new Select<>();

    private Button random = new Button("Generate Random Key & IV");
    private Button encrypt = new Button("Encrypt");
    private Button save = new Button("Save");

    public DashboardView() {
        setId("dashboard-view");
        VerticalLayout wrapper = createWrapper();

        createTitle(wrapper);
        createFormLayout(wrapper);
        createButtonLayout(wrapper);

        // Configure Form
        Binder<User> binder = new Binder<>(User.class);

        // Bind fields. This where you'd define e.g. validation rules
        binder.bindInstanceFields(this);

        random.addClickListener(e -> {
            try {
                binder.writeBean(user);
            } catch (ValidationException ex) {
                ex.printStackTrace();
            }
            byte[] ranKey = new byte[16];
            byte[] ranIV = new byte[16];
            new Random().nextBytes(ranKey);
            new Random().nextBytes(ranIV);
            String resKey = new String(Hex.encodeHex(ranKey));
            String resIV = new String(Hex.encodeHex(ranIV));
            user.setKey(resKey);
            user.setIV(resIV);
            binder.readBean(user);
        });

        encrypt.addClickListener(e -> {
            try {
                binder.writeBean(user);
            } catch (ValidationException ex) {
                ex.printStackTrace();
            }
            Crypto crypto = new AESCrypto();
            byte[] key = null;
            byte[] IV = null;
            String enc = null;

            // Check key input
            if (user.getKey().getBytes().length == 16) {
                key = user.getKey().getBytes();
            } else {
                try {
                    if (Hex.decodeHex(user.getKey()).length == 16) {
                        key = Hex.decodeHex(user.getKey());
                    } else {
                        throw new DecoderException();
                    }
                } catch (DecoderException ex) {
                    Notification.show("Key should be 16 bytes!");
                    ex.printStackTrace();
                }
            }

            // Check IV input
            if (user.getIV().getBytes().length == 16) {
                IV = user.getIV().getBytes();
            } else {
                try {
                    if (Hex.decodeHex(user.getIV()).length == 16) {
                        IV = Hex.decodeHex(user.getIV());
                    } else {
                        throw new DecoderException();
                    }
                } catch (DecoderException ex) {
                    Notification.show("IV should be 16 bytes!");
                    ex.printStackTrace();
                }
            }

            // encrypt the input
            enc = crypto.encrypt(user.getCipherText().getBytes(), key, IV, user.getAlgorithm(), user.getPadding());
            user.setCipherText(enc);
            binder.readBean(user);
        });

        save.addClickListener(e -> {
            try {
                binder.writeBean(user);
            } catch (ValidationException ex) {
                ex.printStackTrace();
            }

            Logger logger = Logger.getLogger("com.encryption.application");
            String id = bs.saveToDB(user);
            if (id != null){
                Notification.show("Save Successfully! The insertion id is: " + id);
                logger.info("Save Successfully! The insertion id is: " + id);
            } else {
                Notification.show("Save failed!");
                logger.info("Save failed!");
            }
        });

        add(wrapper);
    }

    private void createTitle(VerticalLayout wrapper) {
        H1 h1 = new H1("Simple Encryption Implementation");
        wrapper.add(h1);
    }

    private VerticalLayout createWrapper() {
        VerticalLayout wrapper = new VerticalLayout();
        wrapper.setId("wrapper");
        wrapper.setSpacing(false);
        return wrapper;
    }

    private void createFormLayout(VerticalLayout wrapper) {
        FormLayout formLayout = new FormLayout();
        padding.setItems("PKCS5Padding", "ISO10126Padding", "NoPadding");
        padding.setValue("PKCS5Padding");
        algorithm.setItems("CBC", "CTR");
        algorithm.setValue("CBC");
        FormLayout.FormItem cipherTextFormItem = addFormItem(wrapper, formLayout,
                cipherText, "Cipher Text");
        formLayout.setColspan(cipherTextFormItem, 2);
        addFormItem(wrapper, formLayout, key, "Key");
        addFormItem(wrapper, formLayout, IV, "IV");
        addFormItem(wrapper, formLayout, padding, "Padding");
        addFormItem(wrapper, formLayout, algorithm, "Algorithm");
    }

    private void createButtonLayout(VerticalLayout wrapper) {
        HorizontalLayout buttonLayout = new HorizontalLayout();
        buttonLayout.addClassName("button-layout");
        buttonLayout.setWidthFull();
        buttonLayout.setJustifyContentMode(FlexComponent.JustifyContentMode.END);
        random.addThemeVariants(ButtonVariant.LUMO_ICON);
        encrypt.addThemeVariants(ButtonVariant.LUMO_ICON);
        save.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        buttonLayout.add(random);
        buttonLayout.add(encrypt);
        buttonLayout.add(save);
        wrapper.add(buttonLayout);
    }

    private FormLayout.FormItem addFormItem(VerticalLayout wrapper,
                                            FormLayout formLayout, Component field, String fieldName) {
        FormLayout.FormItem formItem = formLayout.addFormItem(field, fieldName);
        wrapper.add(formLayout);
        field.getElement().getClassList().add("full-width");
        return formItem;
    }
}
