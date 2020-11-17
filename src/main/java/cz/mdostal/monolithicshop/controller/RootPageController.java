package cz.mdostal.monolithicshop.controller;

import org.apache.log4j.Logger;

import cz.mdostal.monolithicshop.facade.ProductFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Support controller
 */
@Controller
@RequestMapping("/")
public class RootPageController {

    // flag for test data import
    private boolean isTestDataInitialized = false;

    private Logger logger = Logger.getLogger(RootPageController.class);

    @Autowired
    ProductFacade productFacade;

    @RequestMapping(method = RequestMethod.GET)
    public String sayHello(ModelMap model) {
        model.addAttribute("greeting", "Welcome to Sample Monolith Shop");
        return "welcome";
    }

    @RequestMapping(value = "/helloagain", method = RequestMethod.GET)
    public String sayHelloAgain(ModelMap model) {
        model.addAttribute("greeting", "Hello Again, from Welcome to Sample Monolith Shop");

        return "welcome";
    }

    /**
     * Calls a Test util class to import some sample data to the DB
     * @param model
     * @return a result page
     */
    @RequestMapping(value = "/import", method = RequestMethod.GET)
    @Deprecated
    public String importTestData(ModelMap model) {
        if (!isTestDataInitialized) {
            //productFacade.initializeTestData();
            isTestDataInitialized = true;
            model.addAttribute("message", "Import successful");
        } else {
            model.addAttribute("message", "Import already done. No changes");
        }
        return "import";
    }

}