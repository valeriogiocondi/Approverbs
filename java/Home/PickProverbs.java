package com.valeriogiocondi.user.proverbsapp.Home;

import android.content.Context;

import com.valeriogiocondi.user.proverbsapp.System.DatabaseManager;
import com.valeriogiocondi.user.proverbsapp.System.SystemSettings;


public class PickProverbs {

    Context parentContext;
    DatabaseManager dbHandler;
    SystemSettings system;

    public PickProverbs(Context context) {

        this.parentContext = context;
    }

    public String getProverb() {

        system = new SystemSettings(parentContext);
        String tableName = "proverbs_"+system.get("language");

        dbHandler = new DatabaseManager(parentContext, null, null, 1);

        return dbHandler.queryDb("SELECT * FROM "+tableName+" ORDER BY RANDOM() LIMIT 1;", tableName).trim();
    }

    public String getLanguage() {

        switch (system.get("language")) {

            case "italian": {

                return "Proverbio Italiano";
            }
            case "english": {

                return "Proverbio Inglese";
            }
            case "german": {

                return "Proverbio Tedesco";
            }
            case "latin": {

                return "Proverbio Latino";
            }
            case "spanish": {

                return "Proverbio Spagnolo";
            }
            case "chinese": {

                return "Proverbio Cinese";
            }
            default: {
                return "";
            }
        }
    }
}
