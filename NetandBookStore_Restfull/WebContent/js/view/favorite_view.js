var app = app || {};
var ENTER_KEY = 13;

$(function() {
	'use strict';
	
	////////////////////////////////////////////////////////////////
	// APP VIEW!!!
	app.FavoriteView = Backbone.View.extend({

		// Instead of generating a new element, bind to the existing skeleton of
		// the App already present in the HTML.
		el: '#favorite_view',
		
		// 단일 아이템에 대한 템플릿 함수를 캐쉬한다.
	    template: _.template( $('#favorite_template').html() ),
	    
	    
		initialize: function() {

		},

		// Re-rendering the App just means refreshing the statistics -- the rest
		// of the app doesn't change.
		render: function() {
			this.$el.html( this.template() );
		    return this;
		}

	});
	
});