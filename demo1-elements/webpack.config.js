const path = require("path");
const HtmlWebPackPlugin = require("html-webpack-plugin");
const CopyWebpackPlugin = require('copy-webpack-plugin');
const CircularDependencyPlugin = require('circular-dependency-plugin');

module.exports = {
	module: {
		rules: [
			{
				test: /\.js$/,
				exclude: /node_modules\/(?!(alexandria-ui-elements)\/).*/,
				options: {
					rootMode: "upward",
					presets: ['@babel/preset-env'],
					cacheDirectory: true
				},
				loader: "babel-loader"
			},
			{
				test: /\.html$/,
				loader: "html-loader"
			},
			{
				test: /\.css$/,
				loader: 'style-loader!css-loader'
			}
		]
	},
	entry : {
		'homeTemplate' : './gen/apps/HomeTemplate.js'
	},
	output: {
		path: "/Users/miguelmexicano/Downloads/ejemplo-grafo/out/production/demo1-elements/www/demo1-elements",
		publicPath: '$basePath/demo1-elements/',
		filename: "[name].js"
	},
	resolve: {
		alias: {
			"app-elements": path.resolve(__dirname, '.'),
			"demo1-elements": path.resolve(__dirname, '.')
		}
	},
	plugins: [
		new CircularDependencyPlugin({
			failOnError: false,
			allowAsyncCycles: false,
			cwd: process.cwd(),
		}),
		new CopyWebpackPlugin([{
			from: 'res',
			to: './res'
		}]),
		new HtmlWebPackPlugin({
			hash: true,
			title: "Test UI",
			chunks: ['homeTemplate'],
			template: "./src/homeTemplate.html",
			filename: "./homeTemplate.html"
		})
	]
};