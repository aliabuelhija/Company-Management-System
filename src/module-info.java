module ExperimentFX {
    requires javafx.graphics;
    requires javafx.controls;
    requires javafx.fxml;

    opens AliAbuElhija_AlaaBadra.view;
    opens AliAbuElhija_AlaaBadra.model;
    opens AliAbuElhija_AlaaBadra.controller;
    opens AliAbuElhija_AlaaBadra.listeners;
    opens AliAbuElhija_AlaaBadra.main;
}