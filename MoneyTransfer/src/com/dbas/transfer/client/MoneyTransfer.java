package com.dbas.transfer.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.RootPanel;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class MoneyTransfer implements EntryPoint {

	private TextBox
	
	// Create a handler for the sendButton
	class SendHandler implements ClickHandler {
		/**
		 * Fired when the user clicks on the sendButton.
		 */
		public void onClick(ClickEvent event) {
			amount = Double.parseDouble(s)
			
			
			
			
			transferService.sendMoney(
					
					
					
					new AsyncCallback<Boolean>() {
						public void onFailure(Throwable caught) {
							System.out
									.println("Failure trying to contact server: "
											+ caught.getMessage());
						}

						public void onSuccess(Boolean result) {
							System.out
									.println("Got msg from server: " + result);
						}
					});
		}

		/**
		 * Send the name from the nameField to the server and wait for a
		 * response.
		 */
		private void sendNameToServer() {
			
		}
	}

	/**
	 * Create a remote service proxy to talk to the server-side Greeting
	 * service.
	 */
	private final ServerServiceAsync transferService = GWT
			.create(ServerService.class);

	/**
	 * This is the entry point method.
	 */
	public void onModuleLoad() {
		final Button sendButton = new Button("Send");

		// We can add style names to widgets
		sendButton.addStyleName("sendButton");

		// Add the nameField and sendButton to the RootPanel
		// Use RootPanel.get() to get the entire body element
		RootPanel.get("sendButtonContainer").add(sendButton);

		// Add a handler to send the name to the server
		MyHandler handler = new MyHandler();
		sendButton.addClickHandler(handler);
	}
}
