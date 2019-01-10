import { YiAdminAppPage } from './app.po';

describe('yi-admin-app App', () => {
  let page: YiAdminAppPage;

  beforeEach(() => {
    page = new YiAdminAppPage();
  });

  it('should display message saying app works', () => {
    page.navigateTo();
    expect(page.getParagraphText()).toEqual('app works!');
  });
});
