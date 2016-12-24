// This shows a different way of testing a component, check about for a simpler one
import { Component } from '@angular/core';

import { TestBed } from '@angular/core/testing';

import { IdentifiersComponent } from './identifiers.component';

describe('Identifiers Component', () => {
  const html = '<my-identifiers></my-identifiers>';

  beforeEach(() => {
    TestBed.configureTestingModule({declarations: [IdentifiersComponent, TestComponent]});
    TestBed.overrideComponent(TestComponent, { set: { template: html }});
  });

  it('should ...', () => {
    const fixture = TestBed.createComponent(TestComponent);
    fixture.detectChanges();
    expect(fixture.nativeElement.children[0].textContent).toContain('Identifiers Page!');
  });

});

@Component({selector: 'my-test', template: ''})
class TestComponent { }
