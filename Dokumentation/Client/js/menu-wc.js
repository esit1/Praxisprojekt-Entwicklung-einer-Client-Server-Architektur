'use strict';

customElements.define('compodoc-menu', class extends HTMLElement {
    constructor() {
        super();
        this.isNormalMode = this.getAttribute('mode') === 'normal';
    }

    connectedCallback() {
        this.render(this.isNormalMode);
    }

    render(isNormalMode) {
        let tp = lithtml.html(`
        <nav>
            <ul class="list">
                <li class="title">
                    <a href="index.html" data-type="index-link">client documentation</a>
                </li>

                <li class="divider"></li>
                ${ isNormalMode ? `<div id="book-search-input" role="search"><input type="text" placeholder="Type to search"></div>` : '' }
                <li class="chapter">
                    <a data-type="chapter-link" href="index.html"><span class="icon ion-ios-home"></span>Getting started</a>
                    <ul class="links">
                        <li class="link">
                            <a href="overview.html" data-type="chapter-link">
                                <span class="icon ion-ios-keypad"></span>Overview
                            </a>
                        </li>
                        <li class="link">
                            <a href="index.html" data-type="chapter-link">
                                <span class="icon ion-ios-paper"></span>README
                            </a>
                        </li>
                                <li class="link">
                                    <a href="dependencies.html" data-type="chapter-link">
                                        <span class="icon ion-ios-list"></span>Dependencies
                                    </a>
                                </li>
                    </ul>
                </li>
                    <li class="chapter modules">
                        <a data-type="chapter-link" href="modules.html">
                            <div class="menu-toggler linked" data-toggle="collapse" ${ isNormalMode ?
                                'data-target="#modules-links"' : 'data-target="#xs-modules-links"' }>
                                <span class="icon ion-ios-archive"></span>
                                <span class="link-name">Modules</span>
                                <span class="icon ion-ios-arrow-down"></span>
                            </div>
                        </a>
                        <ul class="links collapse " ${ isNormalMode ? 'id="modules-links"' : 'id="xs-modules-links"' }>
                            <li class="link">
                                <a href="modules/AppModule.html" data-type="entity-link" >AppModule</a>
                                    <li class="chapter inner">
                                        <div class="simple menu-toggler" data-toggle="collapse" ${ isNormalMode ?
                                            'data-target="#components-links-module-AppModule-251e6aa8c61f3fa8423fdbf9b067117f"' : 'data-target="#xs-components-links-module-AppModule-251e6aa8c61f3fa8423fdbf9b067117f"' }>
                                            <span class="icon ion-md-cog"></span>
                                            <span>Components</span>
                                            <span class="icon ion-ios-arrow-down"></span>
                                        </div>
                                        <ul class="links collapse" ${ isNormalMode ? 'id="components-links-module-AppModule-251e6aa8c61f3fa8423fdbf9b067117f"' :
                                            'id="xs-components-links-module-AppModule-251e6aa8c61f3fa8423fdbf9b067117f"' }>
                                            <li class="link">
                                                <a href="components/AppComponent.html" data-type="entity-link" data-context="sub-entity" data-context-id="modules" >AppComponent</a>
                                            </li>
                                            <li class="link">
                                                <a href="components/MainScreenComponent.html" data-type="entity-link" data-context="sub-entity" data-context-id="modules" >MainScreenComponent</a>
                                            </li>
                                            <li class="link">
                                                <a href="components/PageNotFoundComponent.html" data-type="entity-link" data-context="sub-entity" data-context-id="modules" >PageNotFoundComponent</a>
                                            </li>
                                            <li class="link">
                                                <a href="components/SelectionSelfServiceStandComponent.html" data-type="entity-link" data-context="sub-entity" data-context-id="modules" >SelectionSelfServiceStandComponent</a>
                                            </li>
                                        </ul>
                                    </li>
                            </li>
                            <li class="link">
                                <a href="modules/AppRoutingModuleModule.html" data-type="entity-link" >AppRoutingModuleModule</a>
                            </li>
                            <li class="link">
                                <a href="modules/LoginAndRegistrationModuleModule.html" data-type="entity-link" >LoginAndRegistrationModuleModule</a>
                                    <li class="chapter inner">
                                        <div class="simple menu-toggler" data-toggle="collapse" ${ isNormalMode ?
                                            'data-target="#components-links-module-LoginAndRegistrationModuleModule-5806f5ee6a955fe85a0274e8c637ec58"' : 'data-target="#xs-components-links-module-LoginAndRegistrationModuleModule-5806f5ee6a955fe85a0274e8c637ec58"' }>
                                            <span class="icon ion-md-cog"></span>
                                            <span>Components</span>
                                            <span class="icon ion-ios-arrow-down"></span>
                                        </div>
                                        <ul class="links collapse" ${ isNormalMode ? 'id="components-links-module-LoginAndRegistrationModuleModule-5806f5ee6a955fe85a0274e8c637ec58"' :
                                            'id="xs-components-links-module-LoginAndRegistrationModuleModule-5806f5ee6a955fe85a0274e8c637ec58"' }>
                                            <li class="link">
                                                <a href="components/ContactComponent.html" data-type="entity-link" data-context="sub-entity" data-context-id="modules" >ContactComponent</a>
                                            </li>
                                            <li class="link">
                                                <a href="components/DataProtectionComponent.html" data-type="entity-link" data-context="sub-entity" data-context-id="modules" >DataProtectionComponent</a>
                                            </li>
                                            <li class="link">
                                                <a href="components/FooterComponent.html" data-type="entity-link" data-context="sub-entity" data-context-id="modules" >FooterComponent</a>
                                            </li>
                                            <li class="link">
                                                <a href="components/FormComponent.html" data-type="entity-link" data-context="sub-entity" data-context-id="modules" >FormComponent</a>
                                            </li>
                                            <li class="link">
                                                <a href="components/ImprintComponent.html" data-type="entity-link" data-context="sub-entity" data-context-id="modules" >ImprintComponent</a>
                                            </li>
                                            <li class="link">
                                                <a href="components/LoginAndRegistrationComponent.html" data-type="entity-link" data-context="sub-entity" data-context-id="modules" >LoginAndRegistrationComponent</a>
                                            </li>
                                            <li class="link">
                                                <a href="components/LoginFormComponent.html" data-type="entity-link" data-context="sub-entity" data-context-id="modules" >LoginFormComponent</a>
                                            </li>
                                            <li class="link">
                                                <a href="components/LogoComponent.html" data-type="entity-link" data-context="sub-entity" data-context-id="modules" >LogoComponent</a>
                                            </li>
                                            <li class="link">
                                                <a href="components/PasswordResetComponent.html" data-type="entity-link" data-context="sub-entity" data-context-id="modules" >PasswordResetComponent</a>
                                            </li>
                                            <li class="link">
                                                <a href="components/RegistrationFormComponent.html" data-type="entity-link" data-context="sub-entity" data-context-id="modules" >RegistrationFormComponent</a>
                                            </li>
                                        </ul>
                                    </li>
                            </li>
                            <li class="link">
                                <a href="modules/LoginAndRegistrationRoutesModule.html" data-type="entity-link" >LoginAndRegistrationRoutesModule</a>
                            </li>
                            <li class="link">
                                <a href="modules/MainScreenModule.html" data-type="entity-link" >MainScreenModule</a>
                                    <li class="chapter inner">
                                        <div class="simple menu-toggler" data-toggle="collapse" ${ isNormalMode ?
                                            'data-target="#components-links-module-MainScreenModule-926e60d310d690871bcce5e2ba6390f9"' : 'data-target="#xs-components-links-module-MainScreenModule-926e60d310d690871bcce5e2ba6390f9"' }>
                                            <span class="icon ion-md-cog"></span>
                                            <span>Components</span>
                                            <span class="icon ion-ios-arrow-down"></span>
                                        </div>
                                        <ul class="links collapse" ${ isNormalMode ? 'id="components-links-module-MainScreenModule-926e60d310d690871bcce5e2ba6390f9"' :
                                            'id="xs-components-links-module-MainScreenModule-926e60d310d690871bcce5e2ba6390f9"' }>
                                            <li class="link">
                                                <a href="components/AddUserSelfComponent.html" data-type="entity-link" data-context="sub-entity" data-context-id="modules" >AddUserSelfComponent</a>
                                            </li>
                                            <li class="link">
                                                <a href="components/ChangeGoodsComponent.html" data-type="entity-link" data-context="sub-entity" data-context-id="modules" >ChangeGoodsComponent</a>
                                            </li>
                                            <li class="link">
                                                <a href="components/ChangeSelfComponent.html" data-type="entity-link" data-context="sub-entity" data-context-id="modules" >ChangeSelfComponent</a>
                                            </li>
                                            <li class="link">
                                                <a href="components/ChangeUserComponent.html" data-type="entity-link" data-context="sub-entity" data-context-id="modules" >ChangeUserComponent</a>
                                            </li>
                                            <li class="link">
                                                <a href="components/CreateUserComponent.html" data-type="entity-link" data-context="sub-entity" data-context-id="modules" >CreateUserComponent</a>
                                            </li>
                                            <li class="link">
                                                <a href="components/EndOfDayComponent.html" data-type="entity-link" data-context="sub-entity" data-context-id="modules" >EndOfDayComponent</a>
                                            </li>
                                            <li class="link">
                                                <a href="components/EndOfDayPdfComponent.html" data-type="entity-link" data-context="sub-entity" data-context-id="modules" >EndOfDayPdfComponent</a>
                                            </li>
                                            <li class="link">
                                                <a href="components/FooterComponent.html" data-type="entity-link" data-context="sub-entity" data-context-id="modules" >FooterComponent</a>
                                            </li>
                                            <li class="link">
                                                <a href="components/GoodsComponent.html" data-type="entity-link" data-context="sub-entity" data-context-id="modules" >GoodsComponent</a>
                                            </li>
                                            <li class="link">
                                                <a href="components/LogoutComponent.html" data-type="entity-link" data-context="sub-entity" data-context-id="modules" >LogoutComponent</a>
                                            </li>
                                            <li class="link">
                                                <a href="components/MainMenuComponent.html" data-type="entity-link" data-context="sub-entity" data-context-id="modules" >MainMenuComponent</a>
                                            </li>
                                            <li class="link">
                                                <a href="components/MonthComponent.html" data-type="entity-link" data-context="sub-entity" data-context-id="modules" >MonthComponent</a>
                                            </li>
                                            <li class="link">
                                                <a href="components/NavbarComponent.html" data-type="entity-link" data-context="sub-entity" data-context-id="modules" >NavbarComponent</a>
                                            </li>
                                            <li class="link">
                                                <a href="components/NewEndComponent.html" data-type="entity-link" data-context="sub-entity" data-context-id="modules" >NewEndComponent</a>
                                            </li>
                                            <li class="link">
                                                <a href="components/NewGoodsComponent.html" data-type="entity-link" data-context="sub-entity" data-context-id="modules" >NewGoodsComponent</a>
                                            </li>
                                            <li class="link">
                                                <a href="components/NewReceiptComponent.html" data-type="entity-link" data-context="sub-entity" data-context-id="modules" >NewReceiptComponent</a>
                                            </li>
                                            <li class="link">
                                                <a href="components/OverviewEndComponent.html" data-type="entity-link" data-context="sub-entity" data-context-id="modules" >OverviewEndComponent</a>
                                            </li>
                                            <li class="link">
                                                <a href="components/OverviewGoodsComponent.html" data-type="entity-link" data-context="sub-entity" data-context-id="modules" >OverviewGoodsComponent</a>
                                            </li>
                                            <li class="link">
                                                <a href="components/OverviewReceiptComponent.html" data-type="entity-link" data-context="sub-entity" data-context-id="modules" >OverviewReceiptComponent</a>
                                            </li>
                                            <li class="link">
                                                <a href="components/OverviewSelfComponent.html" data-type="entity-link" data-context="sub-entity" data-context-id="modules" >OverviewSelfComponent</a>
                                            </li>
                                            <li class="link">
                                                <a href="components/OverviewUserComponent.html" data-type="entity-link" data-context="sub-entity" data-context-id="modules" >OverviewUserComponent</a>
                                            </li>
                                            <li class="link">
                                                <a href="components/ReceiptComponent.html" data-type="entity-link" data-context="sub-entity" data-context-id="modules" >ReceiptComponent</a>
                                            </li>
                                            <li class="link">
                                                <a href="components/SelfServiceStandComponent.html" data-type="entity-link" data-context="sub-entity" data-context-id="modules" >SelfServiceStandComponent</a>
                                            </li>
                                            <li class="link">
                                                <a href="components/SettingsGoodsComponent.html" data-type="entity-link" data-context="sub-entity" data-context-id="modules" >SettingsGoodsComponent</a>
                                            </li>
                                            <li class="link">
                                                <a href="components/StatisticsComponent.html" data-type="entity-link" data-context="sub-entity" data-context-id="modules" >StatisticsComponent</a>
                                            </li>
                                            <li class="link">
                                                <a href="components/UserComponent.html" data-type="entity-link" data-context="sub-entity" data-context-id="modules" >UserComponent</a>
                                            </li>
                                            <li class="link">
                                                <a href="components/YearComponent.html" data-type="entity-link" data-context="sub-entity" data-context-id="modules" >YearComponent</a>
                                            </li>
                                        </ul>
                                    </li>
                            </li>
                            <li class="link">
                                <a href="modules/MainScreenRoutesModule.html" data-type="entity-link" >MainScreenRoutesModule</a>
                            </li>
                </ul>
                </li>
                    <li class="chapter">
                        <div class="simple menu-toggler" data-toggle="collapse" ${ isNormalMode ? 'data-target="#components-links"' :
                            'data-target="#xs-components-links"' }>
                            <span class="icon ion-md-cog"></span>
                            <span>Components</span>
                            <span class="icon ion-ios-arrow-down"></span>
                        </div>
                        <ul class="links collapse " ${ isNormalMode ? 'id="components-links"' : 'id="xs-components-links"' }>
                            <li class="link">
                                <a href="components/FooterComponent-1.html" data-type="entity-link" >FooterComponent</a>
                            </li>
                        </ul>
                    </li>
                        <li class="chapter">
                            <div class="simple menu-toggler" data-toggle="collapse" ${ isNormalMode ? 'data-target="#injectables-links"' :
                                'data-target="#xs-injectables-links"' }>
                                <span class="icon ion-md-arrow-round-down"></span>
                                <span>Injectables</span>
                                <span class="icon ion-ios-arrow-down"></span>
                            </div>
                            <ul class="links collapse " ${ isNormalMode ? 'id="injectables-links"' : 'id="xs-injectables-links"' }>
                                <li class="link">
                                    <a href="injectables/AuthenticationService.html" data-type="entity-link" >AuthenticationService</a>
                                </li>
                                <li class="link">
                                    <a href="injectables/ConvertDateService.html" data-type="entity-link" >ConvertDateService</a>
                                </li>
                                <li class="link">
                                    <a href="injectables/ServerCommunicationServiceService.html" data-type="entity-link" >ServerCommunicationServiceService</a>
                                </li>
                            </ul>
                        </li>
                    <li class="chapter">
                        <div class="simple menu-toggler" data-toggle="collapse" ${ isNormalMode ? 'data-target="#interfaces-links"' :
                            'data-target="#xs-interfaces-links"' }>
                            <span class="icon ion-md-information-circle-outline"></span>
                            <span>Interfaces</span>
                            <span class="icon ion-ios-arrow-down"></span>
                        </div>
                        <ul class="links collapse " ${ isNormalMode ? ' id="interfaces-links"' : 'id="xs-interfaces-links"' }>
                            <li class="link">
                                <a href="interfaces/EndOfDay.html" data-type="entity-link" >EndOfDay</a>
                            </li>
                            <li class="link">
                                <a href="interfaces/EndOfTheDayOverview.html" data-type="entity-link" >EndOfTheDayOverview</a>
                            </li>
                            <li class="link">
                                <a href="interfaces/Goods.html" data-type="entity-link" >Goods</a>
                            </li>
                            <li class="link">
                                <a href="interfaces/GoodsName.html" data-type="entity-link" >GoodsName</a>
                            </li>
                            <li class="link">
                                <a href="interfaces/Receipt.html" data-type="entity-link" >Receipt</a>
                            </li>
                            <li class="link">
                                <a href="interfaces/SelfServiceStand.html" data-type="entity-link" >SelfServiceStand</a>
                            </li>
                            <li class="link">
                                <a href="interfaces/Tab.html" data-type="entity-link" >Tab</a>
                            </li>
                            <li class="link">
                                <a href="interfaces/Unit.html" data-type="entity-link" >Unit</a>
                            </li>
                        </ul>
                    </li>
                    <li class="chapter">
                        <div class="simple menu-toggler" data-toggle="collapse" ${ isNormalMode ? 'data-target="#miscellaneous-links"'
                            : 'data-target="#xs-miscellaneous-links"' }>
                            <span class="icon ion-ios-cube"></span>
                            <span>Miscellaneous</span>
                            <span class="icon ion-ios-arrow-down"></span>
                        </div>
                        <ul class="links collapse " ${ isNormalMode ? 'id="miscellaneous-links"' : 'id="xs-miscellaneous-links"' }>
                            <li class="link">
                                <a href="miscellaneous/enumerations.html" data-type="entity-link">Enums</a>
                            </li>
                            <li class="link">
                                <a href="miscellaneous/variables.html" data-type="entity-link">Variables</a>
                            </li>
                        </ul>
                    </li>
                        <li class="chapter">
                            <a data-type="chapter-link" href="routes.html"><span class="icon ion-ios-git-branch"></span>Routes</a>
                        </li>
                    <li class="chapter">
                        <a data-type="chapter-link" href="coverage.html"><span class="icon ion-ios-stats"></span>Documentation coverage</a>
                    </li>
                    <li class="divider"></li>
                    <li class="copyright">
                        Documentation generated using <a href="https://compodoc.app/" target="_blank">
                            <img data-src="images/compodoc-vectorise.png" class="img-responsive" data-type="compodoc-logo">
                        </a>
                    </li>
            </ul>
        </nav>
        `);
        this.innerHTML = tp.strings;
    }
});