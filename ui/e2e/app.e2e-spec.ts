import { AppPage } from './app.po';

describe('smartsoftware-filemanager App', () => {
  let page: AppPage;

  beforeEach(() => {
    page = new AppPage();
  });

  it('doshit', () => {
    page.navigateTo();
    expect(page.getParagraphText()).toEqual('File Manager').then(
      () => {
        page.login().then(() => {
          console.log('fsdq');
        });
      }
    );
  });
});
