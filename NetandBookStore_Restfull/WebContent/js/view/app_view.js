var app = app || {};
var ENTER_KEY = 13;

$(function() {
	'use strict';
	
	////////////////////////////////////////////////////////////////
	// APP VIEW!!!
	app.AppView = Backbone.View.extend({

		// Instead of generating a new element, bind to the existing skeleton of
		// the App already present in the HTML.
		el: '#main_view',
		initialize: function() {

			this.favoirte_view = new app.FavoriteView();
		    this.search_view = new app.SearchView();
		    this.resultlist_view = new app.ResultListView();
			this.render();
		},

		// Re-rendering the App just means refreshing the statistics -- the rest
		// of the app doesn't change.
		render: function() {
			this.favoirte_view.render();
			this.search_view.render();
			this.resultlist_view.render();
			return this;
		}

	});
	
});

$(function(){
		
	// Kick things off by creating the **App**.
	new app.AppView();
	
	

});