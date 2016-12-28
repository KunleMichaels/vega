import { TestBed } from '@angular/core/testing';
import { DashboardComponent } from './dashboard.component';
import { provideRoutes } from '@angular/router';
import { ApiService } from '../shared';

describe('Dashboard Component', () => {
  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [DashboardComponent],
      providers: [ApiService, provideRoutes([])],
    });
  });

  it('should ...', () => {
    const fixture = TestBed.createComponent(DashboardComponent);
    fixture.detectChanges();
    expect(fixture.nativeElement.children[0].textContent).toContain('Hello, this is Vega Transit!');
  });

});
