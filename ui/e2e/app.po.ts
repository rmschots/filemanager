import { browser, by, element } from 'protractor';

export class AppPage {
  navigateTo() {
    return browser.get('/');
  }

  getParagraphText() {
    return element(by.css('fm-root .home-text')).getText();
  }

  login() {
    return element(by.id('google-login-link')).click();
  }
}
