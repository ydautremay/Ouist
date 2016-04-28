define([
    'require',
    'module',
    '{angular}/angular',
    '{w20-core}/modules/ui'
], function (require, module, angular) {
    'use strict';

    var config = module && module.config() || {},
        gameManagement = angular.module('GameManagement', ['ui.bootstrap']);
    gameManagement.value('storedGame', {});
    gameManagement.value('editedGame', {});

    gameManagement.factory('GameService', ['$resource', function ($resource) {
        var Game = $resource(require.toUrl(config.apiUrl + 'games/:gameId'), {gameId: '@id'}, {
            'save': {method: 'POST'}
        });

        return {
            allGames: function (success, error) {
                return Game.query({}, success, error);
            },

            allPaginatedGames: function (pageIndex, pageSize, success, error) {
                return Game.query({pageIndex: pageIndex - 1, pageSize: pageSize}, success, error);
            },

            game: function (id) {
                return Game.get({gameId: id});
            },

            addGame: function (players, success, error) {
                var newGame = new Game();
                newGame.players = players;
                newGame.$save(success, error);
            },

            deleteGame: function(game, success, error) {
                Game.delete({gameId: game.gameId}, success, error);
            }
        };
    }]);

    gameManagement.controller('ModalGameController', ['$scope', '$modalInstance', 'modalTitle', function ($scope, $modalInstance, modalTitle) {
        $scope.modalTitle = modalTitle;
        $scope.players = new Array();
        $scope.newPlayer = "";
        $scope.ok = function () {
            $modalInstance.close($scope.players);
        };
        $scope.cancel = function () {
            $modalInstance.dismiss('cancel');
        };
        $scope.add = function() {
            $scope.players.push($scope.newPlayer);
            $scope.newPlayer = "";
        };
        $scope.remove = function(player) {
            var index = $scope.players.indexOf(player);
            $scope.players.splice(index, 1);
        };
    }]);

    gameManagement.controller('ModalDeleteGameController', ['$scope', '$modalInstance', 'game', 'modalTitle', function ($scope, $modalInstance, game, modalTitle) {
        $scope.modalTitle = modalTitle;
        $scope.game = game;
        $scope.ok = function () {
            $modalInstance.close(game);
        };
        $scope.cancel = function () {
            $modalInstance.dismiss('cancel');
        };
    }]);

    gameManagement.controller('GameController', ['$scope', '$modal', '$timeout', 'GameService', function ($scope, $modal, $timeout, gameService) {

        function modal(title, template, controller, game, callback) {
            var modalInstance = $modal.open({
                templateUrl: template,
                controller: controller,
                resolve: {
                    game: function () { return game; },
                    modalTitle: function() { return title; }
                }
            });
            modalInstance.result.then(function(data) { callback(data); });
        }

        function getGamesSuccess(data){
            $scope.paginatedGames = data;
            //$scope.pagingGames.totalServerItems = $scope.paginatedGames.$viewInfo.resultSize;
            //if ($scope.paginatedGames.length) {
            //    $scope.activeGame = $scope.paginatedGames[0];
            //}
        }

        function getGamesError(err) {
            throw new Error('Could not get game ' + err.message);
        }

        function getGames() {
            gameService.allGames(getGamesSuccess, getGamesError);
        }

        $scope.pagingGames = {
            pageSize: 6,
            currentPage: 1,
            totalServerItems: 0
        };

        $scope.pageGameChanged = function() {
            getGames();
        };

        $scope.setActiveGame = function(game) {
            $scope.activeGame = game;
        };

        $scope.createNewGame = function() {
            modal('Add a game', 'modalGame.html', 'ModalGameController', {}, function(players) {
                gameService.addGame(players,
                    function() {
                        getGames();
                    },
                    function(err) {
                        throw new Error('Could not add new game ' + err.message);
                    });
            });
        };

        $scope.deleteGame = function(game) {
            modal('Delete a game', 'modalConfirmGame.html', 'ModalDeleteGameController', game, function (game) {
                gameService.deleteGame(game,
                    function() {
                        getGames();
                    },
                    function(err) {
                        throw new Error('Could not delete game ' + err.message);
                    });
            });
        };

        getGames();
    }]);

    return {
        angularModules: ['GameManagement']
    };
});