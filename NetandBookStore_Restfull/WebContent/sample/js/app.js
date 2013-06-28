var app = app || {};
var ENTER_KEY = 13;

$(function() {
	'use strict';
	
	////////////////////////////////////////////////////////////////
	// BS Model
	app.BSElement = Backbone.Model.extend({
		defaults: {
			title: '',
			book_id: '',
			book_link: '',
			highlight: ''
		}
	});
	
	////////////////////////////////////////////////////////////////
	// book Collection
	var BSList = Backbone.Collection.extend({

		// Reference to this collection's model.
		model: app.BSElement
		
	});

	// Create our global collection of **BSs**.
	app.BSLists = new BSList();
	
	
	////////////////////////////////////////////////////////////////
	// APP VIEW!!!
	app.AppView = Backbone.View.extend({

		// Instead of generating a new element, bind to the existing skeleton of
		// the App already present in the HTML.
		el: '#result',

		initialize: function() {
			
			jQuery.get("./search_1.xml", function (data, textStatus, jqXHR) {
			    console.log("Get resposne:");
			    console.dir(data);
			    console.log(textStatus);
			    console.dir(jqXHR);
			});
			
			
			
			this.render();
		},

		// Re-rendering the App just means refreshing the statistics -- the rest
		// of the app doesn't change.
		render: function() {
			this.$el.html( "rendering...." );
			return this;
		}

	});


	

});

$(function(){
		
	// Kick things off by creating the **App**.
	new app.AppView();
	
});