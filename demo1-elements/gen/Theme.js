import { createTheme } from '@material-ui/core/styles';

const Theme = (function () {
	var theme = null;
	var provider = {
		create: () => {
			theme = createTheme({
				palette : {

				},
				typography : {

				},
				formats: {

				}
			});
			return theme;
		},
		get: () => {
			return theme;
		},
	};
	return provider;
})();

export default Theme;