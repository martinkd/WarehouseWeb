var app = angular.module('app2', []);

app.controller('ctrl2', function($scope, $http) {
	$scope.getAllSuppliersItems = function() {
		$http.get('suppliers').success(function(response) {
			$scope.items = response;
		});
	}

	$scope.searchAllISuppliersItems = function(searchName) {
		$http.get("suppliers?searchName=" + searchName).success(
				function(response) {
					$scope.items = response;
					$scope.searchName = "";
				});
	}
	
	$scope.order = function(item, quantity) {
		var itemData = {
				id : item.id,
				quantity : quantity
			};

			$http({
				url : 'warehouse',
				method : "POST",
				params : itemData
			}).success(function(response) {
				if (response == "") {
					alert("Order success");
				} else {
					alert("invalid input: " + response);
				}
			});
	}

});