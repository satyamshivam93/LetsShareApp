import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import './vendor';
import { LetsShareAppSharedModule } from 'app/shared/shared.module';
import { LetsShareAppCoreModule } from 'app/core/core.module';
import { LetsShareAppAppRoutingModule } from './app-routing.module';
import { LetsShareAppHomeModule } from './home/home.module';
import { LetsShareAppEntityModule } from './entities/entity.module';
// jhipster-needle-angular-add-module-import JHipster will add new module here
import { MainComponent } from './layouts/main/main.component';
import { NavbarComponent } from './layouts/navbar/navbar.component';
import { FooterComponent } from './layouts/footer/footer.component';
import { PageRibbonComponent } from './layouts/profiles/page-ribbon.component';
import { ErrorComponent } from './layouts/error/error.component';

@NgModule({
  imports: [
    BrowserModule,
    LetsShareAppSharedModule,
    LetsShareAppCoreModule,
    LetsShareAppHomeModule,
    // jhipster-needle-angular-add-module JHipster will add new module here
    LetsShareAppEntityModule,
    LetsShareAppAppRoutingModule,
  ],
  declarations: [MainComponent, NavbarComponent, ErrorComponent, PageRibbonComponent, FooterComponent],
  bootstrap: [MainComponent],
})
export class LetsShareAppAppModule {}
