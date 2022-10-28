import styled from "styled-components";

import "App.css";

const Main = styled.div`
  width: 100%;
  // header 만큼 뺴기
  height: calc(100% - 80px);
  display: flex;
  align-items: center;
  justify-content: center;

  @media screen and (max-width: 768px) {
    height: calc(100% - 40px);
  }
`;
const Wrapper = styled.div`
  text-align: center;
`;

const Title = styled.div`
  margin-bottom: 2rem;
  font-family: SCDream9;
  @media screen and (max-width: 768px) {
    margin-bottom: 1rem;
  }
`;
const TitleBlack = styled.span`
  font-size: 9.6rem;

  @media screen and (max-width: 768px) {
    font-size: 4.8rem;
  }
`;
const TitleRed = styled(TitleBlack)`
  color: #ff0000;
`;

const KoreanContent = styled.div``;
const EnglishContent = styled.div`
  padding: 30px 10px;
  margin-top: 20px;
  background-color: #f0f5f9;
  border-radius: 20px;
`;

const Content = styled.div``;
const ContentBlack = styled.span`
  font-size: 2.6rem;

  @media screen and (max-width: 768px) {
    font-size: 1.3rem;
  }
`;
const ContentRed = styled(ContentBlack)`
  color: red;
`;

const Intro = () => {
  return (
    <Main>
      <Wrapper>
        <Title>
          <TitleRed>JK&nbsp;</TitleRed>
          <TitleBlack>Evaluation</TitleBlack>
        </Title>
        <KoreanContent>
          <Content>
            <ContentRed>JK&nbsp;</ContentRed>
            <ContentBlack>
              Evaluation을 통해 자신의 영어 실력을 정확하게 평가하고,
            </ContentBlack>
          </Content>
          <Content>
            <ContentRed>자신만의 </ContentRed>
            <ContentBlack>
              '맞춤형 커리큘럼 및 교재'를 추천 받으세요.
            </ContentBlack>
          </Content>
        </KoreanContent>
        <EnglishContent>
          <Content>
            <ContentBlack>
              Accurately evaluate your English skills with&nbsp;
            </ContentBlack>
            <ContentRed>JK&nbsp;</ContentRed>
            <ContentBlack>Evaluation.</ContentBlack>
          </Content>
          <Content>
            <ContentBlack>Get</ContentBlack>
            <ContentRed>&nbsp;your own&nbsp;</ContentRed>
            <ContentBlack>customized curriculum and textbooks.</ContentBlack>
          </Content>
        </EnglishContent>
      </Wrapper>
    </Main>
  );
};

export default Intro;
