var libraryModule = angular.module('libraryApp', ['ngRoute', 'ngCookies']);

libraryModule.config(["$routeProvider", function($routeProvider){
	return $routeProvider.when("/",{
		redirectTo: "/home"
	}).when("/home", {
		templateUrl: "home.html"
	}).when("/admin", {
		templateUrl: "admin.html"
	}).when("/admin/author", {
		templateUrl: "admin/author.html"
	}).when("/admin/addAuthor", {
		templateUrl: "admin/addauthor.html"
	}).when("/admin/viewAuthors", {
		templateUrl: "admin/viewauthors.html"
	}).when("/admin/book", {
		templateUrl: "admin/book.html"
	}).when("/admin/addBook", {
		templateUrl: "admin/addbook.html"
	}).when("/admin/viewBooks", {
		templateUrl: "admin/viewbooks.html"
	})
}])

libraryModule.controller('authorCtrl', function($scope, $http, $window){
	$http.get('http://localhost:8080/lms/admin/viewAuthors/0').
	success(function(data){
		$scope.authors = data;
	});
	
	$scope.addAuthor = function(){
		var author = {authorName: $scope.author.authorName};
		$http.post('http://localhost:8080/lms/admin/addAuthor', author).
			success(function(data){
				$window.location.href = "#/admin/viewAuthors";
		});
	}
})

libraryModule.controller('bookCtrl', function($scope, $http, $window){
	$http.get('http://localhost:8080/lms/admin/viewBooks/0').
	success(function(data){
		$scope.books = data;
	});
	
	$http.get('http://localhost:8080/lms/admin/addBook').
	success(function(data){
		$scope.book = data;
		$scope.authors = $scope.book.authors;
		$scope.publishers = $scope.book.publishers;
		$scope.genres = $scope.book.genres;
	});
	
	$scope.addBook = function(){
		var book = { title: $scope.b.title, 
				authors: [ {authorId : $scope.b.authorId} ], 
				publisher: {publisherId : $scope.b.publisherId},
				genres: [ {genre_id: $scope.b.genre_id} ] };
		console.log(book);
		
		$http.post('http://localhost:8080/lms/admin/addBook', book).
		success(function(data){
			$window.location.href = "#/admin/viewBooks";
		});
	}
})
