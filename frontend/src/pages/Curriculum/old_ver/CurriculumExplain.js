import styled from "styled-components";

import "App.css";

// 총 width가 400이므로 380 + 20이다.
const Content = styled.div`
  box-sizing: border-box;
  margin: 15px 0 15px 20px;
  width: 380px;
  border: 2px solid black;
`;

const Writing = styled.div`
  font-family: SCDream5;
`;
const Listening = styled(Writing)``;
const Speaking = styled(Writing)``;
const Reading = styled(Writing)``;
const Grammar = styled(Writing)``;

const Part = styled.div`
  margin-bottom: 24px;
  font-size: 24px;
`;
const BookIntro = styled.div`
  margin-bottom: 15px;
`;
const BookLink = styled.div`
  margin-bottom: 15px;
`;

const CurriculumExplain = () => {
  return (
    <Content>
      <Writing>
        <Part>- Writing</Part>
        <BookIntro>책 소개 : ~~~</BookIntro>
        <BookLink>책 구매 링크 :</BookLink>
      </Writing>
      <Listening>
        <Part>- Listening</Part>
        <BookIntro>책 소개 : ~~~</BookIntro>
        <BookLink>책 구매 링크 :</BookLink>
      </Listening>
      <Speaking>
        <Part>- Speaking</Part>
        <BookIntro>책 소개 : ~~~</BookIntro>
        <BookLink>책 구매 링크 :</BookLink>
      </Speaking>
      <Reading>
        <Part>- Reading</Part>
        <BookIntro>책 소개 : ~~~</BookIntro>
        <BookLink>책 구매 링크 :</BookLink>
      </Reading>
      <Grammar>
        <Part>- Grammar</Part>
        <BookIntro>책 소개 : ~~~</BookIntro>
        <BookLink>책 구매 링크 :</BookLink>
      </Grammar>
    </Content>
  );
};

export default CurriculumExplain;
