package org.openmrs.module.htmlformentry.handler;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.openmrs.api.context.Context;
import org.openmrs.module.htmlformentry.FormEntryContext.Mode;
import org.openmrs.module.htmlformentry.FormEntrySession;
import org.openmrs.module.htmlformentry.FormSubmissionController;
import org.openmrs.module.htmlformentry.Translator;

/**
 * Handles the {@code <submit>} tag
 */
public class SubmitButtonHandler extends SubstitutionTagHandler {
	
	@Override
	public String getName() {
		return "Submit Button";
	}
	
	public String getDescription() {
		return "Button for Form Submission (essential)";
	}
	
	@Override
	protected List<AttributeDescriptor> createAttributeDescriptors() {
		List<AttributeDescriptor> attrs = new ArrayList<AttributeDescriptor>();
		
		attrs.add(new AttributeDescriptor("submitLabel", "Button Label:", false, "", "text"));
		attrs.add(new AttributeDescriptor("submitCodel", "Code:", false, "", "text"));
		attrs.add(new AttributeDescriptor("submitStyle", "Style:", false, "", "text"));
		
		return attrs;
	}
	
    @Override
    protected String getSubstitution(FormEntrySession session, FormSubmissionController controllerActions,
            Map<String, String> parameters) {

        String submitLabel = null;
        String submitClass = "submitButton";

    	//handle defaults
    	if (session.getContext().getMode() == Mode.VIEW) {
            return "";
        }
    	else if (session.getContext().getMode() == Mode.EDIT) {
    		submitLabel = Context.getMessageSourceService().getMessage("htmlformentry.saveChangesButton");
        } else {
        	submitLabel = Context.getMessageSourceService().getMessage("htmlformentry.enterFormButton");
        }

    	//check for custom label & style
        if (parameters.containsKey("submitLabel")) {
        	submitLabel =  parameters.get("submitLabel");
        }
        if (parameters.containsKey("submitCode")) {
	    	Translator trans = session.getContext().getTranslator();
	    	submitLabel = trans.translate(Context.getLocale().toString(), parameters.get("submitCode"));
        }
        if (parameters.containsKey("submitClass")) {
	    	submitClass = parameters.get("submitClass");
        }
	
        //render it
    	return "<input type=\"button\" class=\"" + submitClass + "\" value=\"" + submitLabel + "\" onClick=\"submitHtmlForm()\"/>";
    }

}
