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
			link: '',
			highlight: ''
		},
		
		parse:function (response) {
	        //response.id = response._id;
	        this.highlight = jQuery.base64.decode(response.highlight);
	        return response;
	    }
	});
	
	////////////////////////////////////////////////////////////////
	// book Collection
	var BSList = Backbone.Collection.extend({

		// Reference to this collection's model.
		model: app.BSElement,
		
		url:"./book/search?keyword=Sample",
		
		parse:function (response) {
	        //response.id = response._id;
	       // this.highlight = jQuery.base64.decode(response.highlight);
			console.log( response );
	        return response;
	    },
	    
	    search_keyword:function( keyword ){
	    	
	    }
		
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
			
			this.collection = app.BSLists;
			
			this.collection.fetch(
					{ 
					    url: "./book/search?keyword=Sample", 
					    success: function() {
					          console.log("JSON file load was successful", app.BSLists);
					      },
					    error: function(){
					       console.log('There was some error in loading and processing the JSON file');
					       console.log( arguments );
						},
						//emulateJSON:true,
						//emulateHTTP:true
					  }
					);

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