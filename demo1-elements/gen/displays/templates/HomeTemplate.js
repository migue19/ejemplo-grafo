import React from "react";
import { withStyles } from '@material-ui/core/styles';
import UiBlock from "alexandria-ui-elements/src/displays/components/Block";
import UiText from "alexandria-ui-elements/src/displays/components/Text";
import UiTemplate from "alexandria-ui-elements/src/displays/components/Template";
import HomeTemplateNotifier from "alexandria-ui-elements/gen/displays/notifiers/TemplateNotifier";
import HomeTemplateRequester from "alexandria-ui-elements/gen/displays/requesters/TemplateRequester";
import DisplayFactory from 'alexandria-ui-elements/src/displays/DisplayFactory';
import { withSnackbar } from 'notistack';

const styles = theme => ({});

class HomeTemplate extends UiTemplate {

	constructor(props) {
		super(props);
		this.notifier = new HomeTemplateNotifier(this);
		this.requester = new HomeTemplateRequester(this);
	};

	render() {
		const display = !this.state.visible ? {display:'none'} : undefined;
		const className = "layout vertical center-justified" + (this.hiddenClass() !== "" ? " " + this.hiddenClass() : "");
		return(
			<UiBlock layout="vertical" style={{...this.props.style,...display}}>
				<UiText context={this._context.bind(this)} owner={this._owner.bind(this)} id="a_2012307258" mode="normal" value="Hola">
				</UiText>
			</UiBlock>
		);
	}
}

export default withStyles(styles, { withTheme: true })(withSnackbar(HomeTemplate));
DisplayFactory.register("HomeTemplate", withStyles(styles, { withTheme: true })(withSnackbar(HomeTemplate)));