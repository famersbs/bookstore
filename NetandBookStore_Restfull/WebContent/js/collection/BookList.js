var app = app || {};
var ENTER_KEY = 13;

$(function() {
	'use strict';
	
	////////////////////////////////////////////////////////////////
	// book Collection
	var BookList = Backbone.Collection.extend({

		reservation:false, 
		nowloading:false, 
		reservation_keyword:"",
		
		// Reference to this collection's model.
		model: app.Bookelement,

		parse:function (response) {
	        //response.id = response._id;
	       // this.highlight = jQuery.base64.decode(response.highlight);
			console.log( response );
	        return response;
	    },
	    
	    search_keyword:function( keyword ){
	    	
	    	//if( !this.nowloading )
	    	//{
	    	
		    	this.reset();
		    	this.fetch(
		    			{ 
					    url: "./book/search?keyword="+keyword, 
					    success: function() {
					          console.log("JSON file load was successful", app.bookList);
					          
					      },
					    error: function(){
					       console.log('There was some error in loading and processing the JSON file');
					       console.log( arguments );
						}
						//emulateJSON:true,
						//emulateHTTP:true
					  }
					);
	    	//}else{
	    	//	this.reservation = true;
	    	//	reservation_keyword = keyword;
	    	//}
	    }
		
	});

	// Create our global collection of **BSs**.
	app.bookList = new BookList();
	
	
});
