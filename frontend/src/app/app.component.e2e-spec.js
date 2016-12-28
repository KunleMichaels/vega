describe('App', function () {

  beforeEach(function () {
    browser.get('/');
  });

  it('should have a title', function () {
    expect(browser.getTitle()).toEqual("Vega Transit | an electronic transit fare payment system");
  });

  it('should have <app-toolbar>', function () {
    expect(element(by.css('my-app app-toolbar')).isPresent()).toEqual(true);
  });

  it('should have a <app-toolbar> title', function () {
    expect(element(by.css('app-toolbar div')).getText()).toEqual('Vega Transit');
  });

  it('should have <main>', function () {
    expect(element(by.css('my-app main')).isPresent()).toEqual(true);
  });

  it('should have <footer>', function () {
    expect(element(by.css('my-app footer')).getText()).toEqual("View on GitHub: SocialEdge Vega");
  });

});
